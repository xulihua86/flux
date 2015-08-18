window.onload = function() {
	init(50, "legend", 640, 960, main, LEvent.INIT);
};
var initData = [ {
	name : "blank",
	path : "images/blank.png"
}, {
	name : "background",
	path : "images/scr_bg.jpg"
}, {
	name : "purseAfter",
	path : "images/scr_01.png"
}, {
	name : "moneyBack",
	path : "images/scr_03.png"
}, {
	name : "linesBefore",
	path : "images/scr_04.png"
}, {
	name : "linesAfter",
	path : "images/scr_08.png"
}, {
	name : "award",
	path : "images/scr_05.png"
}, {
	name : "finger",
	path : "images/scr_06.png"
}, {
	name : "purseBefore",
	path : "images/scr_07.png"
} ];

// 各种图层
var backgroundLayer, moneyBackLayer, awardLayer, moneyLayer, linesLayer, purseLayer, fingerLayer, tipLayer, ruleLayer;
// 所有的文件对象
var files;

// 开始扯钱包时的y坐标
var beginY;

// 钱包Y轴最大值
var maxY = 400;
// 钱包Y轴最小是
var minY = 300;

// 获奖文本
var moneyTextLabel;

function main() {
	if (LGlobal.canTouch) {
		LGlobal.stageScale = LStageScaleMode.EXACT_FIT;
		LSystem.screen(LStage.FULL_SCREEN);
	}

	LMouseEventContainer.set(LMouseEvent.MOUSE_DOWN, true);
	LMouseEventContainer.set(LMouseEvent.MOUSE_UP, true);
	LMouseEventContainer.set(LMouseEvent.MOUSE_MOVE, true);

	backgroundLayer = new LSprite();
	addChild(backgroundLayer);

	LLoadManage.load(initData, null, initGame);
}

/**
 * 初始化游戏
 * 
 * @param result
 *            解析初始化文件的结果
 */
function initGame(result) {
	// 获取获奖信息
	moneyTextLabel = document.getElementById("money-text").value;
    if(moneyTextLabel == "endU"){
    	moneyTextLabel = "您的抽奖机会已用完，感谢您的参与！";
    }else if(moneyTextLabel == "canN"){
    	moneyTextLabel = "本次没有中奖哦，感谢您的参与";
    }
   	if (!files) {
		files = result;
	}

	// 初始化背景图片
	var backgroundBit = new LBitmap(new LBitmapData(files["background"]));
	backgroundBit.scaleX = LGlobal.width / backgroundBit.getWidth();
	backgroundBit.scaleY = LGlobal.height / backgroundBit.getHeight();
	backgroundLayer.addChild(backgroundBit);

	// 开始游戏
	startGame();

}

/**
 * 游戏开始
 */
function startGame() {
	// 初始化奖
	moneyBackLayer = new LSprite();
	var moneyBackBit = new LBitmap(new LBitmapData(files["moneyBack"]));
	moneyBackLayer.addChild(moneyBackBit);
	moneyBackLayer.x = 155;
	moneyBackLayer.y = 240;

	awardLayer = new LSprite();
	var awardBit = new LBitmap(new LBitmapData(files["award"]));
	awardLayer.addChild(awardBit);
	awardLayer.x = 70;
	awardLayer.y = 100;
	moneyBackLayer.addChild(awardLayer);

	backgroundLayer.addChildAt(moneyBackLayer, 1);

	// 初始绳子
	linesLayer = new LSprite();
	var linesBit = new LBitmap(new LBitmapData(files["linesBefore"]));
	linesLayer.addChild(linesBit);
	linesLayer.x = 0;
	linesLayer.y = 0;
	backgroundLayer.addChildAt(linesLayer, 2);

	// 初始化钱包
	purseLayer = new LSprite();
	var purseBeforeBit = new LBitmap(new LBitmapData(files["purseBefore"]));
	purseLayer.addChild(purseBeforeBit);
	purseLayer.x = 75;
	purseLayer.y = 340;

	// 初始化手指
	fingerLayer = new LSprite();
	var fingerBit = new LBitmap(new LBitmapData(files["finger"]));
	fingerLayer.addChild(fingerBit);
	fingerLayer.x = 470;
	fingerLayer.y = 390;
	purseLayer.addChild(fingerLayer);

	backgroundLayer.addChildAt(purseLayer, 2);

	backgroundLayer.addEventListener(LMouseEvent.MOUSE_DOWN, pressDown);
	//backgroundLayer.addEventListener(LEvent.ENTER_FRAME, tipFrame);

	// 初始化活动规则跳转
	ruleLayer = new LSprite();
	var ruleBit = new LBitmap(new LBitmapData(files["blank"]));
	ruleBit.scaleX = 100;
	ruleBit.scaleY = 30;
	ruleLayer.addChild(ruleBit);
	ruleLayer.x = 20;
	ruleLayer.y = 900;
	backgroundLayer.addChild(ruleLayer);

	ruleLayer.addEventListener(LMouseEvent.MOUSE_DOWN, onRule);
}

/**
 * 准备跳转到游戏规则页面
 */
function onRule() {
	ruleLayer.removeEventListener(LMouseEvent.MOUSE_DOWN, onRule);
	ruleLayer.addEventListener(LMouseEvent.MOUSE_UP, doRule);
}
/**
 * 跳转到游戏规则页面
 */
function doRule() {
	ruleLayer.removeEventListener(LMouseEvent.MOUSE_UP, doRule);
	ruleLayer.addEventListener(LMouseEvent.MOUSE_DOWN, onRule);
	window.location.href = "rule.jsp";
}
/**
 * 循环提示
 */
function tipFrame() {
	if (fingerLayer.alpha >= 1) {
		fingerLayer.y = 390;
		fingerLayer.alpha = 0;
	}
	fingerLayer.y = fingerLayer.y + 2;
	fingerLayer.alpha = fingerLayer.alpha + 0.02;
}

/**
 * 准备拉红包，向下推拽开始拉扯红包
 */
function pressDown(event) {
	backgroundLayer.removeEventListener(LMouseEvent.MOUSE_DOWN, pressDown);
	//backgroundLayer.removeEventListener(LEvent.ENTER_FRAME, tipFrame);

	fingerLayer.remove();

	backgroundLayer.addEventListener(LMouseEvent.MOUSE_MOVE, move);
	backgroundLayer.addEventListener(LMouseEvent.MOUSE_UP, pressUp);
	beginY = event.offsetY;
}

function pressUp() {
	backgroundLayer.removeEventListener(LMouseEvent.MOUSE_UP, pressUp);
	backgroundLayer.addEventListener(LMouseEvent.MOUSE_DOWN, pressDown);
	backgroundLayer.removeEventListener(LMouseEvent.MOUSE_MOVE, move);
}
/**
 * 开始拉扯红包
 * 
 * @param event
 */
function move(event) {
	// 在minY-maxY范围内移动钱包
	var moveLength = event.offsetY - beginY;
	if (purseLayer.y + moveLength > maxY) {
		purseLayer.y = maxY;
	} else if (purseLayer.y + moveLength < minY) {
		purseLayer.y = minY;
	} else {
		purseLayer.y += moveLength;
	}

	beginY = event.offsetY;
	if (purseLayer.y >= maxY) {
		purseLayer.remove();

		purseLayer = new LSprite();
		var purseBeforeBit = new LBitmap(new LBitmapData(files["purseAfter"]));
		purseLayer.addChild(purseBeforeBit);
		purseLayer.x = 75;
		purseLayer.y = 400;
		backgroundLayer.addChild(purseLayer);

		linesLayer.remove();
		linesLayer = new LSprite();
		var linesBit = new LBitmap(new LBitmapData(files["linesAfter"]));
		linesLayer.addChild(linesBit);
		linesLayer.x = 0;
		linesLayer.y = 0;
		backgroundLayer.addChildAt(linesLayer, 2);

		backgroundLayer.removeEventListener(LMouseEvent.MOUSE_DOWN, pressDown);
		backgroundLayer.removeEventListener(LMouseEvent.MOUSE_MOVE, move);
		backgroundLayer.addEventListener(LEvent.ENTER_FRAME, awardFrame);
	}
}

/**
 * 显示抽奖结果
 */
function awardFrame() {
	awardLayer.alpha = awardLayer.alpha - 0.02;
	if (awardLayer.alpha <= 0) {
		awardLayer.remove();
		backgroundLayer.removeEventListener(LEvent.ENTER_FRAME, awardFrame);
		backgroundLayer.addEventListener(LEvent.ENTER_FRAME, moneyFrame);

		moneyLayer = new LSprite();
		var moneyText = new LTextField();
		moneyText.setWordWrap(true, 50);
		moneyText.color = "#e00140";
		moneyText.font = "黑体";
		moneyText.width = 180;
		moneyText.size = 30;
		moneyText.text = moneyTextLabel;
		moneyLayer.x = 55;
		moneyLayer.y = 90;
		moneyLayer.alpha = 0;
		moneyLayer.addChild(moneyText);
		moneyBackLayer.addChild(moneyLayer);
	}
}

function moneyFrame() {
	moneyLayer.alpha = moneyLayer.alpha + 0.02;
	if (moneyLayer.alpha >= 1) {
		moneyLayer.alpha = 1;
		backgroundLayer.removeEventListener(LEvent.ENTER_FRAME, moneyFrame);

		// 跳转到中奖信息页面
		var ts = document.getElementById("money-text").value;
		if(ts == "endU" || ts == "canN"){
			showMenu();
		}else{
			document.forms[0].submit();
			//window.location.href = url_path+"outerview/prize/toResult.action?openid="+openid+"&prize_name="+prize_name;	
		}
		
	}
}