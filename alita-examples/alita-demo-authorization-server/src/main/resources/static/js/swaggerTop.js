$(document).ready(function() {
	var checkInfo_descriptionDivTimer;
	var isImportantInfo = true;
	var isChangeLogShowed = false;
	var isStateCodeShowed = true;
	var buildMakedown = function() {
		if($(".info_description").html()){
			window.clearInterval(checkInfo_descriptionDivTimer);
			$(".info_description").append("<div id='importantInfoTitle'>重要说明[-]</div><div id='importantInfoContent'>暂无内容</div>" +
				"<div id='changeLogTitle'>改动记录[+]</div><div id='changeLogContent'>暂无内容</div>");
			$("#changeLogTitle,#stateCodeTitle,#importantInfoTitle").css({"cursor":"pointer", "font-size": "30px", "font-weight": "bold"});
			$("#changeLogContent").hide();
			$("#importantInfoTitle").click(function (){
				if(isImportantInfo){
					$("#importantInfoTitle").text("重要说明[+]");
					isImportantInfo = false;
				} else {
					$("#importantInfoTitle").text("重要说明[-]");
					isImportantInfo = true;
				}
				$("#importantInfoContent").toggle(isImportantInfo);
			});
			$("#changeLogTitle").click(function (){
				if(isChangeLogShowed){
					$("#changeLogTitle").text("改动记录[+]");
					isChangeLogShowed = false;
				} else {
					$("#changeLogTitle").text("改动记录[-]");
					isChangeLogShowed = true;
				}
				$("#changeLogContent").toggle(isChangeLogShowed);
			});
			
			$.get("/static/importantInfo.md?t=" + new Date(), function(data){
				if(!data){
					return;
				}
				var converter = new showdown.Converter({
					emoji: true,    // 打开 emoji 文字表情支持  例: this is a smile :smile: emoji 被转换为  <p>this is a smile 😄 emoji</p>,  表情列表  https://github.com/showdownjs/showdown/wiki/Emojis
					underline: true,   // 打开下划线支持    __被下划线的__   和   ___空格    一起下划线___
					simplifiedAutoLink: true,      // 打开 url自动转连接功能
					excludeTrailingPunctuationFromURLs: true,  // 自动转url时排除最后的标点符号, 要先打开 simplifiedAutoLink
					strikethrough: true,     //打开删除线支持  ~~被删除线的~~
					tables: true,   // 打开表格支持,例:
					//  | h1    |    h2   |      h3 |
					//  |:------|:-------:|--------:|
					//  | 100   | [a][1]  | ![b][2] |
					//  | *foo* | **bar** | ~~baz~~ |
					openLinksInNewWindow: true   //在浏览器窗口中打开连接

				});
				var html = converter.makeHtml(data);
				$("#importantInfoContent").html(html);
			});
			$.get("/static/changelog.md?t=" + new Date(), function(data){
				if(!data){
					return;
				}
				var converter = new showdown.Converter({
					emoji: true,    // 打开 emoji 文字表情支持  例: this is a smile :smile: emoji 被转换为  <p>this is a smile 😄 emoji</p>,  表情列表  https://github.com/showdownjs/showdown/wiki/Emojis
					underline: true,   // 打开下划线支持    __被下划线的__   和   ___空格    一起下划线___
					simplifiedAutoLink: true,      // 打开 url自动转连接功能
					excludeTrailingPunctuationFromURLs: true,  // 自动转url时排除最后的标点符号, 要先打开 simplifiedAutoLink
					strikethrough: true,     //打开删除线支持  ~~被删除线的~~
					tables: true,   // 打开表格支持,例:
					//  | h1    |    h2   |      h3 |
					//  |:------|:-------:|--------:|
					//  | 100   | [a][1]  | ![b][2] |
					//  | *foo* | **bar** | ~~baz~~ |
					openLinksInNewWindow: true   //在浏览器窗口中打开连接

				});
				var html = converter.makeHtml(data);
				$("#changeLogContent").html(html);
			});

		}
	}
	checkInfo_descriptionDivTimer = window.setInterval(buildMakedown, 1);
});