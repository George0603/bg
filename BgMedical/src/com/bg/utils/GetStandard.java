package com.bg.utils;

import java.util.ArrayList;
import java.util.List;

public class GetStandard {

	public static List<double[]> get_standard(boolean flag, int sex, String str) {
		List<double[]> list = new ArrayList<double[]>();
		double[] x_y_max = new double[2];

		if (flag) { // 9城市标准
			if (sex == 0) { // 男生
				switch (str) {
				case "height":
					x_y_max[0] = 75.0;
					x_y_max[1] = 140.0;
					list.add(Constant.boy_height_SD2neg_9);
					list.add(Constant.boy_height_SD1neg_9);
					list.add(Constant.boy_height_SD1_9);
					list.add(Constant.boy_height_SD2_9);
					break;
				case "weight":
					x_y_max[0] = 75.0;
					x_y_max[1] = 40.0;
					list.add(Constant.boy_weight_SD2neg_9);
					list.add(Constant.boy_weight_SD1neg_9);
					list.add(Constant.boy_weight_SD1_9);
					list.add(Constant.boy_weight_SD2_9);
					break;
				case "head":
					x_y_max[0] = 75.0;
					x_y_max[1] = 60.0;
					list.add(Constant.boy_head_SD2neg_9);
					list.add(Constant.boy_head_SD1neg_9);
					list.add(Constant.boy_head_SD1_9);
					list.add(Constant.boy_head_SD2_9);
					break;
				case "bmi":
					x_y_max[0] = 75.0;
					x_y_max[1] = 25.0;
					list.add(Constant.boy_bmi_SD2neg_9);
					list.add(Constant.boy_bmi_SD1neg_9);
					list.add(Constant.boy_bmi_SD1_9);
					list.add(Constant.boy_bmi_SD2_9);
					break;
				default:
					break;
				}
			} else { // 女生
				switch (str) {
				case "height":
					x_y_max[0] = 75.0;
					x_y_max[1] = 140.0;
					list.add(Constant.girl_height_SD2neg_9);
					list.add(Constant.girl_height_SD1neg_9);
					list.add(Constant.girl_height_SD1_9);
					list.add(Constant.girl_height_SD2_9);
					break;
				case "weight":
					x_y_max[0] = 75.0;
					x_y_max[1] = 40.0;
					list.add(Constant.girl_weight_SD2neg_9);
					list.add(Constant.girl_weight_SD1neg_9);
					list.add(Constant.girl_weight_SD1_9);
					list.add(Constant.girl_weight_SD2_9);
					break;
				case "head":
					x_y_max[0] = 75.0;
					x_y_max[1] = 60.0;
					list.add(Constant.girl_head_SD2neg_9);
					list.add(Constant.girl_head_SD1neg_9);
					list.add(Constant.girl_head_SD1_9);
					list.add(Constant.girl_head_SD2_9);
					break;
				case "bmi":
					x_y_max[0] = 75.0;
					x_y_max[1] = 25.0;
					list.add(Constant.girl_bmi_SD2neg_9);
					list.add(Constant.girl_bmi_SD1neg_9);
					list.add(Constant.girl_bmi_SD1_9);
					list.add(Constant.girl_bmi_SD2_9);
					break;
				default:
					break;
				}
			}
		} else { // who城市标准
			if (sex == 0) { // 男生
				switch (str) {
				case "height":
					x_y_max[0] = 75.0;
					x_y_max[1] = 130.0;
					list.add(Constant.boy_height_SD2neg);
					list.add(Constant.boy_height_SD1neg);
					list.add(Constant.boy_height_SD1);
					list.add(Constant.boy_height_SD2);
					break;
				case "weight":
					x_y_max[0] = 75.0;
					x_y_max[1] = 40.0;
					list.add(Constant.boy_weight_SD2neg);
					list.add(Constant.boy_weight_SD1neg);
					list.add(Constant.boy_weight_SD1);
					list.add(Constant.boy_weight_SD2);
					break;
				case "head":
					x_y_max[0] = 75.0;
					x_y_max[1] = 60.0;
					list.add(Constant.boy_head_SD2neg);
					list.add(Constant.boy_head_SD1neg);
					list.add(Constant.boy_head_SD1);
					list.add(Constant.boy_head_SD2);
					break;
				case "bmi":
					x_y_max[0] = 75.0;
					x_y_max[1] = 25.0;
					list.add(Constant.boy_bmi_SD2neg);
					list.add(Constant.boy_bmi_SD1neg);
					list.add(Constant.boy_bmi_SD1);
					list.add(Constant.boy_bmi_SD2);
					break;
				default:
					break;
				}
			} else { // 女生
				switch (str) {
				case "height":
					x_y_max[0] = 75.0;
					x_y_max[1] = 130.0;
					list.add(Constant.girl_height_SD2neg);
					list.add(Constant.girl_height_SD1neg);
					list.add(Constant.girl_height_SD1);
					list.add(Constant.girl_height_SD2);
					break;
				case "weight":
					x_y_max[0] = 75.0;
					x_y_max[1] = 40.0;
					list.add(Constant.girl_weight_SD2neg);
					list.add(Constant.girl_weight_SD1neg);
					list.add(Constant.girl_weight_SD1);
					list.add(Constant.girl_weight_SD2);
					break;
				case "head":
					x_y_max[0] = 75.0;
					x_y_max[1] = 60.0;
					list.add(Constant.girl_head_SD2neg);
					list.add(Constant.girl_head_SD1neg);
					list.add(Constant.girl_head_SD1);
					list.add(Constant.girl_head_SD2);
					break;
				case "bmi":
					x_y_max[0] = 75.0;
					x_y_max[1] = 25.0;
					list.add(Constant.girl_bmi_SD2neg);
					list.add(Constant.girl_bmi_SD1neg);
					list.add(Constant.girl_bmi_SD1);
					list.add(Constant.girl_bmi_SD2);
					break;
				default:
					break;
				}
				
			}
		}
		list.add(x_y_max);
		return list;
	}

}
