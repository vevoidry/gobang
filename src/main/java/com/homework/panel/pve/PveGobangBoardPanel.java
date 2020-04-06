package com.homework.panel.pve;

import com.homework.data.MetaData;
import com.homework.panel.GobangBoardPanel;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

//电脑模式的棋盘对象
public class PveGobangBoardPanel extends GobangBoardPanel {
	// 是否轮到电脑落子
	public boolean isComputer;

	// 是否电脑执黑先行，传入true则会电脑执黑先行，否则为玩家执黑先行
	public PveGobangBoardPanel(boolean isComputerFirst) {
		this.isComputer = isComputerFirst;
		if (isComputer) {
			pveRandom();
		} else {
			isComputer = true;
		}
	}

	// 若轮到电脑落子，则在随机位置进行落子
	public void pveRandom() {
		// 判断是否轮到电脑落子，若未轮到电脑落子，则
		if (!isComputer) {
			isComputer = true;
			return;
		}
		// 判断棋盘是否已满，若满，则什么都不做
		if (list.size() >= MetaData.SIDE_NUMBER * MetaData.SIDE_NUMBER) {
			return;
		}
		isComputer = false;
		while (true) {
			// 获取随机坐标
			int[] xy = getXY();
			// 判断该位置是否未落子，若未落子，则进行落子，否则继续循环
			if (array[xy[0]][xy[1]] == 0) {
				draw(xy[0], xy[1]);
				break;
			}
		}
		isComputer = true;
		// 休眠半秒钟，以便电脑落子速度不要太快
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 获取0-18之间的随机整数
	public int[] getRandomXY() {
		int[] xy = new int[2];
		xy[0] = (int) Math.round(Math.random() * (MetaData.SIDE_NUMBER - 1));
		xy[1] = (int) Math.round(Math.random() * (MetaData.SIDE_NUMBER - 1));
		return xy;
	}

	public int[] getXY() {
		// 如果是电脑第一次落子，则随机落子
		if (list.size() == 0) {
			return getRandomXY();
		}
		// 取出最后一步棋子的位置信息
		String theLast = history.get(history.size() - 1);
		int start = theLast.indexOf("(");
		int middle = theLast.indexOf(",");
		int end = theLast.indexOf(")");
		int theLastX = Integer.parseInt(theLast.substring(start + 1, middle));
		int theLastY = Integer.parseInt(theLast.substring(middle + 1, end));
		// 若不是电脑第一次落子，则获取前一个落子的坐标，以其为中心就近原则获取随机落子点
		// 随机顺序有两种，一种是左右上下，一种是下上右左
		// 先获取一个随机数，根据随机数选择上面两种顺序种的一种
		double random = Math.random();
		if (random > 0.5) {
			// 以左右上下的顺序判断是否能落子
			int[] xy = new int[2];
			while (true) {
				for (int i = 1; i <= MetaData.SIDE_NUMBER - 1; i++) {
					if (theLastX - i >= 0 && array[theLastX - i][theLastY] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX - i;
						xy[1] = theLastY;
						return xy;
					} else if (theLastX + i <= MetaData.SIDE_NUMBER - 1
							&& array[theLastX + i][theLastY] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX + i;
						xy[1] = theLastY;
						return xy;
					} else if (theLastY - i >= 0 && array[theLastX][theLastY - i] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX;
						xy[1] = theLastY - i;
						return xy;
					} else if (theLastY + i <= MetaData.SIDE_NUMBER - 1
							&& array[theLastX][theLastY + i] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX;
						xy[1] = theLastY + i;
						return xy;
					} else {
						continue;
					}
				}
			}
		} else {
			// 以下上右左的顺序判断是否能落子
			int[] xy = new int[2];
			while (true) {
				for (int i = 1; i <= MetaData.SIDE_NUMBER - 1; i++) {
					if (theLastY + i <= MetaData.SIDE_NUMBER - 1
							&& array[theLastX][theLastY + i] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX;
						xy[1] = theLastY + i;
						return xy;
					} else if (theLastY - i >= 0 && array[theLastX][theLastY - i] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX;
						xy[1] = theLastY - i;
						return xy;
					} else if (theLastX + i <= MetaData.SIDE_NUMBER - 1
							&& array[theLastX + i][theLastY] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX + i;
						xy[1] = theLastY;
						return xy;
					} else if (theLastX - i >= 0 && array[theLastX - i][theLastY] == MetaData.STATUS_BLANK) {
						xy[0] = theLastX - i;
						xy[1] = theLastY;
						return xy;
					} else {
						continue;
					}
				}
			}
		}
	}

	// 重写draw方法
	public void draw(int x, int y) {
		// 判断所点击的位置是否不存在棋子
		if (array[x][y] != MetaData.STATUS_BLANK) {
			// 如果存在棋子，则什么都不做
			return;
		} else {
			// 判断是由黑方落子还是白方落子
			// 将棋子在棋盘上画出后在修改棋子数组上的相应数据
			Ellipse ellipse = new Ellipse();
			ellipse.setCenterX(x * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setCenterY(y * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setRadiusX(MetaData.RADIUS);
			ellipse.setRadiusY(MetaData.RADIUS);
			if (is_black) {
				ellipse.setFill(Color.BLACK);
				array[x][y] = MetaData.STATUS_BLACK;
				// 将数据保存到棋谱中
				history.add("黑子(" + x + "," + y + ");");
			} else {
				ellipse.setFill(Color.WHITE);
				array[x][y] = MetaData.STATUS_WHITE;
				history.add("白子(" + x + "," + y + ");");
			}
			// 在棋盘上添加棋子，使棋子可见
			this.getChildren().add(ellipse);
			// 将棋子放入棋子列表，用于悔棋
			list.add(ellipse);
			// 转换落子方
			is_black = !is_black;
			// 判断此时是否连成五子，若连成五子则提示游戏结束并保存棋谱
			if (isEnd(x, y)) {
				// 新建提示框
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.titleProperty().set("游戏结束");
				if (is_black) {
					alert.headerTextProperty().set("白棋获胜");
					history.add("白棋获胜");
				} else {
					alert.headerTextProperty().set("黑棋获胜");
					history.add("黑棋获胜");
				}
				// 保存棋谱
				saveHistory();
				// 弹出提示框
				alert.showAndWait();
			} else {
				pveRandom();
			}
		}
	}

}
