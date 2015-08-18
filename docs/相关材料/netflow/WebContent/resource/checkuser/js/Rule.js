window.onload = function() {
	init(50, "legend", 640, 960, main, LEvent.INIT);
};
var initData = [ {
	name : "blank",
	path : "images/blank.png"
}, {
	name : "background",
	path : "images/rule.jpg"
}];

// 各种图层
var backgroundLayer;
// 所有的文件对象
var files;

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
	if (!files) {
		files = result;
	}

	// 初始化背景图片
	var backgroundBit = new LBitmap(new LBitmapData(files["background"]));
	backgroundBit.scaleX = LGlobal.width / backgroundBit.getWidth();
	backgroundBit.scaleY = LGlobal.height / backgroundBit.getHeight();
	backgroundLayer.addChild(backgroundBit);
}