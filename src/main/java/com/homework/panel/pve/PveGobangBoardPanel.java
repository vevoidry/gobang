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

//����ģʽ�����̶���
public class PveGobangBoardPanel extends GobangBoardPanel {
	// �Ƿ��ֵ���������
	public boolean isComputer;

	// �Ƿ����ִ�����У�����true������ִ�����У�����Ϊ���ִ������
	public PveGobangBoardPanel(boolean isComputerFirst) {
		this.isComputer = isComputerFirst;
		if (isComputer) {
			pveRandom();
		} else {
			isComputer = true;
		}
	}

	// ���ֵ��������ӣ��������λ�ý�������
	public void pveRandom() {
		// �ж��Ƿ��ֵ��������ӣ���δ�ֵ��������ӣ���
		if (!isComputer) {
			isComputer = true;
			return;
		}
		// �ж������Ƿ���������������ʲô������
		if (list.size() >= MetaData.SIDE_NUMBER * MetaData.SIDE_NUMBER) {
			return;
		}
		isComputer = false;
		while (true) {
			// ��ȡ�������
			int[] xy = getXY();
			// �жϸ�λ���Ƿ�δ���ӣ���δ���ӣ���������ӣ��������ѭ��
			if (array[xy[0]][xy[1]] == 0) {
				draw(xy[0], xy[1]);
				break;
			}
		}
		isComputer = true;
		// ���߰����ӣ��Ա���������ٶȲ�Ҫ̫��
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ��ȡ0-18֮����������
	public int[] getRandomXY() {
		int[] xy = new int[2];
		xy[0] = (int) Math.round(Math.random() * (MetaData.SIDE_NUMBER - 1));
		xy[1] = (int) Math.round(Math.random() * (MetaData.SIDE_NUMBER - 1));
		return xy;
	}

	public int[] getXY() {
		// ����ǵ��Ե�һ�����ӣ����������
		if (list.size() == 0) {
			return getRandomXY();
		}
		// ȡ�����һ�����ӵ�λ����Ϣ
		String theLast = history.get(history.size() - 1);
		int start = theLast.indexOf("(");
		int middle = theLast.indexOf(",");
		int end = theLast.indexOf(")");
		int theLastX = Integer.parseInt(theLast.substring(start + 1, middle));
		int theLastY = Integer.parseInt(theLast.substring(middle + 1, end));
		// �����ǵ��Ե�һ�����ӣ����ȡǰһ�����ӵ����꣬����Ϊ���ľͽ�ԭ���ȡ������ӵ�
		// ���˳�������֣�һ�����������£�һ������������
		// �Ȼ�ȡһ������������������ѡ����������˳���ֵ�һ��
		double random = Math.random();
		if (random > 0.5) {
			// ���������µ�˳���ж��Ƿ�������
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
			// �����������˳���ж��Ƿ�������
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

	// ��дdraw����
	public void draw(int x, int y) {
		// �ж��������λ���Ƿ񲻴�������
		if (array[x][y] != MetaData.STATUS_BLANK) {
			// ����������ӣ���ʲô������
			return;
		} else {
			// �ж����ɺڷ����ӻ��ǰ׷�����
			// �������������ϻ��������޸����������ϵ���Ӧ����
			Ellipse ellipse = new Ellipse();
			ellipse.setCenterX(x * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setCenterY(y * MetaData.SIDE_WIDTH_UNIT);
			ellipse.setRadiusX(MetaData.RADIUS);
			ellipse.setRadiusY(MetaData.RADIUS);
			if (is_black) {
				ellipse.setFill(Color.BLACK);
				array[x][y] = MetaData.STATUS_BLACK;
				// �����ݱ��浽������
				history.add("����(" + x + "," + y + ");");
			} else {
				ellipse.setFill(Color.WHITE);
				array[x][y] = MetaData.STATUS_WHITE;
				history.add("����(" + x + "," + y + ");");
			}
			// ��������������ӣ�ʹ���ӿɼ�
			this.getChildren().add(ellipse);
			// �����ӷ��������б����ڻ���
			list.add(ellipse);
			// ת�����ӷ�
			is_black = !is_black;
			// �жϴ�ʱ�Ƿ��������ӣ���������������ʾ��Ϸ��������������
			if (isEnd(x, y)) {
				// �½���ʾ��
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.titleProperty().set("��Ϸ����");
				if (is_black) {
					alert.headerTextProperty().set("�����ʤ");
					history.add("�����ʤ");
				} else {
					alert.headerTextProperty().set("�����ʤ");
					history.add("�����ʤ");
				}
				// ��������
				saveHistory();
				// ������ʾ��
				alert.showAndWait();
			} else {
				pveRandom();
			}
		}
	}

}
