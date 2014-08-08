package com.fy.utils;

import java.lang.Math;

public class Num2RMB {
	private final static String[] CN_Digits = { "��", "Ҽ", "�E", "��", "��", "��",
			"½", "��", "��", "��", };

	/**
	 * �������ͻ���ת��Ϊ�����ͻ��� <br/>
	 * ���ߣ�wallimn��ʱ�䣺2009-4-10������09:59:26<br/>
	 * ���ͣ�http://blog.csdn.net/wallimn<br/>
	 * ������<br/>
	 * 
	 * @param moneyValue
	 *            ���ַ�����ʽ�Ľ�С�����֣�������3λ������ȥ��������������
	 * @return
	 */
	public static String CNValueOf(String moneyValue) {
		//ʹ��������ʽ��ȥ��ǰ����㼰�����еĶ���
		String value = moneyValue.replaceFirst("^0+", "");
		value = value.replaceAll(",", "");
		//�ָ�С����������������
		int dot_pos = value.indexOf('.');
		String int_value;
		String fraction_value;
		if (dot_pos == -1) {
			int_value = value;
			fraction_value = "00";
		} else {
			int_value = value.substring(0, dot_pos);
			fraction_value = value.substring(dot_pos + 1, value.length())
					+ "00".substring(0, 2);//Ҳ������0�����ں���ͳһ����
		}

		int len = int_value.length();
		if (len>16) return "ֵ����";
		StringBuffer cn_currency = new StringBuffer();
		String[] CN_Carry = new String[] { "", "��", "��", "��" };
		//���ַ��鴦����������
		int cnt = len/4+(len%4==0?0:1);
		//��ߵ�һ��ĳ���
		int partLen = len-(cnt-1)*4;
		String partValue=null;
		boolean bZero=false;//�й���
		String curCN=null;
		for(int i =0; i<cnt; i++){
			partValue = int_value.substring(0,partLen);
			int_value=int_value.substring(partLen);
			curCN = Part2CN(partValue,i!=0&&!"��".equals(curCN));
			//System.out.println(partValue+":"+curCN);
			//���ϴ�Ϊ�㣬��β�Ϊ�㣬�������			
			if(bZero && !"��".equals(curCN)){
				cn_currency.append("��");
				bZero=false;
			}
			if("��".equals(curCN))bZero=true;
			//�����ֲ����㣬�����������ּ���λ
			if(!"��".equals(curCN)){
				cn_currency.append(curCN);
				cn_currency.append(CN_Carry[cnt-1-i]);
			}
			//�������һ�鳤�Ȳ����⣬�������ȶ�Ϊ4
			partLen=4;
			partValue=null;
		}
		cn_currency.append("Ԫ");
		//����С������
		int fv1 = Integer.parseInt(fraction_value.substring(0,1));
		int fv2 = Integer.parseInt(fraction_value.substring(1,2));
		if(fv1+fv2==0){
			cn_currency.append("��");
		}
		else{
			cn_currency.append(CN_Digits[fv1]).append("��");
			cn_currency.append(CN_Digits[fv2]).append("��");
		}
		return cn_currency.toString();
	}

	/**
	 * ��һ�����֣��������ĸ���ת�������ı�ʾ <br/>
	 * ���ߣ�wallimn��ʱ�䣺2009-4-11������07:41:25<br/>
	 * ���ͣ�http://wallimn.iteye.com<br/>
	 * ������<br/>
	 * 
	 * @param partValue �ַ�����ʽ������
	 * @param bInsertZero �Ƿ���ǰ�������
	 * @return
	 */
	private static String Part2CN(String partValue,boolean bInsertZero) {
		//ʹ��������ʽ��ȥ��ǰ���0
		partValue = partValue.replaceFirst("^0+", "");
		int len = partValue.length();
		if (len == 0)
			return "��";
		StringBuffer sbResult = new StringBuffer();
		int digit;
		String[] CN_Carry = new String[] { "", "ʰ", "��", "Ǫ" };
		for (int i = 0; i < len; i++) {
			digit = Integer.parseInt(partValue.substring(i, i + 1));
			if (digit != 0) {
				sbResult.append(CN_Digits[digit]);
				sbResult.append(CN_Carry[len - 1 - i]);
			} else {
				// ���������һλ�����²�λ��Ϊ�㣬׷����
				if (i != len - 1
						&& Integer.parseInt(partValue.substring(i + 1, i + 2)) != 0)
					sbResult.append("��");
			}
		}
		if(bInsertZero && len!=4)sbResult.insert(0, "��");
		return sbResult.toString();
	}
}
