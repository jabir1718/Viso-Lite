(function(){
"use strict";
function draw(ctx,p){
 const {source,video,videoMode,photoOpacity,blendEnabled,blendMode,blendOpacity,brightness,contrast,saturation,hue,applyTransform,W,H}=p;
 if(!source||photoOpacity<=0)return;
 ctx.save();
 applyTransform(ctx);
 ctx.globalAlpha=(photoOpacity/100)*(blendEnabled?blendOpacity/100:1);
 ctx.globalCompositeOperation=blendEnabled?blendMode:'source-over';
 ctx.filter='brightness('+brightness+'%) contrast('+contrast+'%) saturate('+saturation+'%) hue-rotate('+hue+'deg)';
 ctx.drawImage(videoMode?video:source,-W/2,-H/2,W,H);
 ctx.filter='none';
 ctx.globalCompositeOperation='source-over';
 ctx.restore();
 ctx.globalAlpha=1;
}
window.VisoMediaEffects={draw};
})();
