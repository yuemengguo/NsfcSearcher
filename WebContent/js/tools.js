var Time;
function showLoading(){
	let content = "<div id=\"loading\"><img id=\"loading-prev\" src=\".\/images\/icon-01.png\"><\/img><img id=\"loading-next\" src=\".\/images\/icon-02.png\"><\/img><p>加载中...<\/p><\/div>";
	let bg = "<div id=\"loading-bg\"><\/div>";
	$("body").append(bg);
	$("body").append(content);
	$("#loading-bg").fadeTo(0,0.6);//背景色过滤
	$("#loading").css({"position": "fixed","width": "200px","z-index": "997","height": "230px","overflow": "hidden","top":"50%","left":"50%","margin-left": "-100px","margin-top": "-150px","border": "0"});
	$("#loading-prev").css({"position": "absolute","z-index": "998","margin": "0","width": "200px"});
	$("#loading-next").css({"position": "absolute","z-index": "999","margin":"0","width": "200px"});
	$("#loading p").css({"position":"relative","margin-top":"200px", "text-align":"center","font-size":"17px","color":"white","font-weight":"bold","z-index": "999"});
	$("#loading-bg").css({"position": "fixed","width": "100%","z-index": "990","height": "100%","border": "1px solid transpant","background": "silver","top":"0"});
	let angle = 0;
	Time = setInterval(function(){
		angle+=3;
		$("#loading-prev").rotate(angle);
	},25);

}

function removeLoading(){
	$("#loading").remove();
	$("#loading-bg").remove();
	window.clearInterval(Time);
}