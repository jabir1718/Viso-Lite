(function(){
"use strict";
function draw(ctx,p){
 const {source,video,videoMode,enabled,photoOpacity,blendEnabled,blendMode,blendOpacity,beatEnergy,beatReaction,brightness,contrast,saturation,hue,applyTransform,W,H}=p;
 if(!source||!enabled||photoOpacity<=0)return;
 ctx.save();
 applyTransform(ctx);
 const beatGain=1+(Math.max(0,Math.min(1,beatEnergy))-.0)*.22*(beatReaction/100);
 ctx.globalAlpha=(photoOpacity/100)*(blendEnabled?blendOpacity/100:1);
 ctx.globalCompositeOperation=blendEnabled?blendMode:'source-over';
 ctx.filter='brightness('+(brightness*beatGain)+'%) contrast('+contrast+'%) saturate('+saturation+'%) hue-rotate('+hue+'deg)';
 const mediaSource=videoMode?video:source,sw=videoMode?(video.videoWidth||W):(source.naturalWidth||source.width||W),sh=videoMode?(video.videoHeight||H):(source.naturalHeight||source.height||H);
 const fit=Math.max(W/Math.max(1,sw),H/Math.max(1,sh)),dw=sw*fit,dh=sh*fit;
 ctx.drawImage(mediaSource,-dw/2,-dh/2,dw,dh);
 ctx.filter='none';
 ctx.globalCompositeOperation='source-over';
 ctx.restore();
 ctx.globalAlpha=1;
}
window.VisoMediaEffects={draw};
})();
