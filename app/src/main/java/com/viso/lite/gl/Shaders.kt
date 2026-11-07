package com.viso.lite.gl

object Shaders {

    const val VERT = """
        attribute vec2 aPos;
        varying vec2 vUv;
        void main() {
            vUv = aPos * 0.5 + 0.5;
            gl_Position = vec4(aPos, 0.0, 1.0);
        }
    """

    const val FRAG = """
        precision mediump float;
        varying vec2 vUv;
        uniform vec2 uRes;
        uniform float uTime;
        uniform float uMode;
        uniform float uOpacity;
        uniform float uBands[6];
        uniform sampler2D uMask;
        uniform sampler2D uImage;

        float band(float x) {
            int i = int(clamp(x * 6.0, 0.0, 5.0));
            if (i == 0) return uBands[0];
            if (i == 1) return uBands[1];
            if (i == 2) return uBands[2];
            if (i == 3) return uBands[3];
            if (i == 4) return uBands[4];
            return uBands[5];
        }

        vec3 palette(float t) {
            return 0.5 + 0.5 * cos(6.2831 * (t + vec3(0.0, 0.33, 0.67)));
        }

        void main() {
            vec2 uv = vUv;
            float energy = 0.0;
            vec3 col = vec3(0.0);

            if (uMode < 0.5) {
                float bars = 42.0;
                float x = floor(uv.x * bars) / bars;
                float h = band(x);
                float bar = step(uv.y, h) * step(0.02, fract(uv.x * bars));
                energy = h;
                col = palette(x * 1.4 + uTime * 0.08) * bar * (0.55 + h);
            } else if (uMode < 1.5) {
                vec2 p = uv - 0.5;
                p.x *= uRes.x / uRes.y;
                float r = length(p);
                float a = atan(p.y, p.x) / 6.2831 + 0.5;
                float h = band(a);
                float ring = smoothstep(0.02, 0.0, abs(r - (0.24 + h * 0.22)));
                energy = h;
                col = palette(a + uTime * 0.1) * ring * (0.7 + h * 1.6);
            } else if (uMode < 2.5) {
                float w = sin(uv.x * 26.0 + uTime * 3.0) * 0.5 + 0.5;
                float h = band(uv.x);
                float line = smoothstep(0.05, 0.0, abs(uv.y - 0.5 - (w - 0.5) * h * 0.6));
                energy = h;
                col = mix(vec3(0.1, 0.9, 1.0), vec3(1.0, 0.2, 0.8), w) * line * 1.4;
            } else {
                vec2 p = uv - 0.5;
                p.x *= uRes.x / uRes.y;
                float r = length(p);
                float seed = floor(uTime * 6.0);
                float ang = atan(p.y, p.x) * 6.0 + seed;
                float n = fract(sin(dot(floor(vec2(ang, r * 14.0)), vec2(12.9898, 78.233))) * 43758.5453);
                float h = band(n);
                float dm = step(0.985 - h * 0.5, n) * smoothstep(0.9, 0.1, r);
                energy = h;
                col = palette(n + uTime * 0.2) * dm * (0.8 + h * 2.0);
            }

            col *= 0.9 + energy * 1.1;

            if (uMode > 3.5) {
                float a = texture2D(uMask, uv).a;
                float edge = smoothstep(0.55, 0.95, a) - smoothstep(0.95, 1.0, a);
                col = col * a + palette(0.55 + uTime * 0.1) * edge * (0.6 + energy * 2.0);
            }

            vec4 img = texture2D(uImage, uv);
            if (img.a > 0.01) col = mix(col, img.rgb, img.a * uOpacity);

            gl_FragColor = vec4(col, 1.0);
        }
    """
}
