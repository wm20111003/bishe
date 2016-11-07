package com.centfor.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * POI 解析 2003-2007 版本 Excel 文件
 * 
 */
public class ExcelUtil {

	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static int reading2003Row = 0;
	public static int reading2003total = 1;

	private List<Map<String, String>> read2003Excel(InputStream inputStream, String fileName, String flag) throws IOException {
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		// System.out.println("读取 office 2003 excel 内容如下：");
		for (int i = 5; i <= sheet.getPhysicalNumberOfRows(); i++) {
			System.out.println("解析excel" + i);
			row = sheet.getRow(i);
			if (row != null) {
				Map<String, String> linked = new HashMap<String, String>();
				int total = 0;
				if (flag.equals("market")) {
					total = 2;
					if (row.getLastCellNum() != total) {
						linked.clear();
						linked.put("flag", "false");
						linked.put("row", (i + 1) + "");
						linked.put("FILE_NM", fileName);
						linked.put("ERROR_RSN_DESC", (i + 1) + "模版错误");
						linked.put("ERROR_DESC", "目标客户模版不正确");
						list.add(linked);
						break;
					}

				} else if (flag.equals("order")) {
					total = 4;
					if (row.getLastCellNum() != total) {
						linked.clear();
						linked.put("flag", "false");
						linked.put("row", (i + 1) + "");
						linked.put("FILE_NM", fileName);
						linked.put("ERROR_RSN_DESC", "目标客户模版错误");
						linked.put("ERROR_DESC", "目标客户模版不正确");
						list.add(linked);
						break;
					}
				}

				for (int j = 0; j <= row.getLastCellNum() - 1; j++) {
					try {
						cell = row.getCell(j);
						if (cell != null && !cell.toString().trim().equals("")) {
							DecimalFormat df = new DecimalFormat("0");// 格式化
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
							DecimalFormat nf = new DecimalFormat("0");// 格式化数字
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								if ("@".equals(cell.getCellStyle().getDataFormatString())) {
									value = df.format(cell.getNumericCellValue());
								} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
									value = nf.format(cell.getNumericCellValue());
								} else {
									value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								}
								break;
							case XSSFCell.CELL_TYPE_BOOLEAN:
								value = cell.getBooleanCellValue();
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								value = "";
								break;
							default:
								value = cell.toString();
							}
							linked.put("flag", "true");
							if (j == 1) {
								if (!isMobileNumber(value.toString())) {
									linked.clear();
									linked.put("flag", "false");
									linked.put("row", (i + 1) + "");
									linked.put("FILE_NM", fileName);
									linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "列手机格式错误");
									linked.put("ERROR_DESC", "手机格式错误");
									break;
								}
							}
							if (j == 0) {// 插入客户姓名
								linked.put("LIN_CUST_NM", value.toString());
							} else if (j == 1) {// 客户电话
								linked.put("RESV_MOBNUM", value.toString());
							} else if (j == 2) {// 商品ID
								linked.put("RSVT_MCDS_UNIT_ID", value.toString());
							} else if (j == 3) {// 日期
								linked.put("RSVT_DATE", value.toString());
							}

							// 判断如果营销活动客户没有值默认填写未知
						} else if (j == 0 && row.getLastCellNum() == 2) {
							// linked.clear();
							linked.put("flag", "true");
							linked.put("LIN_CUST_NM", "未知");
						} else {
							linked.clear();
							linked.put("flag", "false");
							linked.put("FILE_NM", fileName);
							linked.put("row", (i + 1) + "");
							linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "列不能为空");
							linked.put("ERROR_DESC", "有字段为空");
							break;
						}
					} catch (Exception e) {
						linked.clear();
						linked.put("flag", "false");
						linked.put("row", (i + 1) + "");
						linked.put("FILE_NM", fileName);
						linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "解析异常");
						linked.put("ERROR_DESC", "解析异常");
						break;
					}
				}

				list.add(linked);
			}
		}
		inputStream.close();
		return list;

	}

	/**
	 * 读取 Office 2007 excel
	 */
	private static List<Map<String, String>> read2007Excel(InputStream inputStream, String fileName, String flag)
			throws IOException {
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		// System.out.println("读取 office 2003 excel 内容如下：");
		for (int i = 5; i <= sheet.getPhysicalNumberOfRows(); i++) {
			System.out.println("解析07版excel" + "第" + i + "行");
			row = sheet.getRow(i);
			if (row != null) {
				Map<String, String> linked = new HashMap<String, String>();
				int total = 0;
				if (flag.equals("market")) {
					total = 2;
					if (row.getLastCellNum() != total) {
						linked.clear();
						linked.put("flag", "false");
						linked.put("row", (i + 1) + "");
						linked.put("FILE_NM", fileName);
						linked.put("ERROR_RSN_DESC", (i + 1) + "模版错误");
						linked.put("ERROR_DESC", "目标客户模版不正确");
						list.add(linked);
						break;
					}

				} else if (flag.equals("order")) {
					total = 4;
					if (row.getLastCellNum() != total) {
						linked.clear();
						linked.put("flag", "false");
						linked.put("row", (i + 1) + "");
						linked.put("FILE_NM", fileName);
						linked.put("ERROR_RSN_DESC", "目标客户模版错误");
						linked.put("ERROR_DESC", "目标客户模版不正确");
						list.add(linked);
						break;
					}
				}

				for (int j = 0; j <= row.getLastCellNum() - 1; j++) {
					try {
						cell = row.getCell(j);
						if (cell != null && !cell.toString().trim().equals("")) {
							DecimalFormat df = new DecimalFormat("0");// 格式化
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
							DecimalFormat nf = new DecimalFormat("0");// 格式化数字
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								if ("@".equals(cell.getCellStyle().getDataFormatString())) {
									value = df.format(cell.getNumericCellValue());
								} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
									value = nf.format(cell.getNumericCellValue());
								} else {
									value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								}
								break;
							case XSSFCell.CELL_TYPE_BOOLEAN:
								value = cell.getBooleanCellValue();
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								value = "";
								break;
							default:
								value = cell.toString();
							}
							linked.put("flag", "true");
							if (j == 1) {
								if (!isMobileNumber(value.toString())) {
									linked.clear();
									linked.put("flag", "false");
									linked.put("row", (i + 1) + "");
									linked.put("FILE_NM", fileName);
									linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "列手机格式错误");
									linked.put("ERROR_DESC", "手机格式错误");
									break;
								}
							}
							if (j == 0) {// 插入客户姓名
								linked.put("LIN_CUST_NM", value.toString());
							} else if (j == 1) {// 客户电话
								linked.put("RESV_MOBNUM", value.toString());
							} else if (j == 2) {// 商品ID
								linked.put("RSVT_MCDS_UNIT_ID", value.toString());
							} else if (j == 3) {// 日期
								linked.put("RSVT_DATE", value.toString());
							}

							// 判断如果营销活动客户没有值默认填写未知
						} else if (j == 0 && row.getLastCellNum() == 2) {
							// linked.clear();
							linked.put("flag", "true");
							linked.put("LIN_CUST_NM", "未知");
						} else {
							linked.clear();
							linked.put("flag", "false");
							linked.put("FILE_NM", fileName);
							linked.put("row", (i + 1) + "");
							linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "列不能为空");
							linked.put("ERROR_DESC", "有字段为空");
							break;
						}
					} catch (Exception e) {
						linked.clear();
						linked.put("flag", "false");
						linked.put("row", (i + 1) + "");
						linked.put("FILE_NM", fileName);
						linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "解析异常");
						linked.put("ERROR_DESC", "解析异常");
						break;
					}
				}

				list.add(linked);
			}
		}
		inputStream.close();
		return list;
	}

	/**
	 * 获取2003Excel第一个sheet页中的行数
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static int getExcel2003RowNum(File file) throws Exception {
		HSSFWorkbook workBook = new HSSFWorkbook(FileUtils.openInputStream(file));
		HSSFSheet sheet = workBook.getSheetAt(0); // 拿到第一个sheet页
		int rows = sheet.getLastRowNum() + 1;// 几行
		return rows;
	}

	/**
	 * 获取2007Excel第一个sheet页中的行数
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static int getExcel2007RowNum(File file) throws Exception {
		XSSFWorkbook workBook = new XSSFWorkbook(FileUtils.openInputStream(file));
		XSSFSheet sheet = workBook.getSheetAt(0);
		int rows = sheet.getLastRowNum() + 1;
		return rows;
	}

	/**
	 * 获得一个2003Excel的第一个sheet页中的有效行数
	 * 
	 * @param file
	 * @param beginRow
	 * @return
	 * @throws Exception
	 */
	private static int getExcel2003ValidRowNum(File file, int beginRow) throws Exception {
		HSSFWorkbook workBook = new HSSFWorkbook(FileUtils.openInputStream(file));
		HSSFSheet sheet = workBook.getSheetAt(0); // 拿到第一个sheet页
		int rows = sheet.getLastRowNum() + 1;// 几行
		HSSFRow tmp = null;
		int i = rows;
		while (true) {
			tmp = sheet.getRow(--i);
			if ((tmp != null && tmp.getCell(4) == null) || (tmp != null && tmp.getCell(4) != null
					&& tmp.getCell(4).getCellType() == HSSFCell.CELL_TYPE_BLANK))
				rows--;
			if (i == beginRow - 1)
				break;
		}
		return rows - beginRow;
	}

	/**
	 * 获得一个2007Excel的第一个sheet页中的有效行数
	 * 
	 * @param file
	 * @param beginRow
	 * @return
	 * @throws Exception
	 */
	private static int getExcel2007ValidRowNum(File file, int beginRow) throws Exception {
		XSSFWorkbook workBook = new XSSFWorkbook(FileUtils.openInputStream(file));
		XSSFSheet sheet = workBook.getSheetAt(0); // 拿到第一个sheet页
		int rows = sheet.getLastRowNum() + 1;// 几行
		XSSFRow tmp = null;
		int i = rows;
		while (true) {
			tmp = sheet.getRow(--i);
			if ((tmp != null && tmp.getCell(4) == null) || (tmp != null && tmp.getCell(4) != null
					&& tmp.getCell(4).getCellType() == HSSFCell.CELL_TYPE_BLANK))
				rows--;
			if (i == beginRow - 1)
				break;
		}
		return rows - beginRow;
	}

	/**
	 * 对外提供读取 Excel的方法
	 * 
	 * @throws Exception
	 */
	public List<Map<String, String>> readExcel(InputStream inputStream, String fileName, String flag) throws Exception {
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(inputStream,fileName,flag);
//			return read2003ExcelJXL(inputStream, 6);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(inputStream, fileName, flag);
		} else {
			throw new IOException("不支持的文件类型!");
		}
	}




	


	private List<Map<String, String>> read2007ExcelToMerchant(InputStream inputStream, String fileName) {
		return null;
	}





	/**
	 * 对外提供读取 Excel有效行数的方法
	 */
	public static int getExcelValidRowNum(File file, int beginRow) throws Exception {
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return getExcel2003ValidRowNum(file, beginRow);
		} else if ("xlsx".equals(extension)) {
			return getExcel2007ValidRowNum(file, beginRow);
		} else {
			throw new Exception("解析Excel行数出错!");
		}
	}

	/**
	 * 对外提供解析Excel行数的方法
	 * 
	 * @param file
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static int getExcelRowNum(File file) throws Exception {
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return getExcel2003RowNum(file);
		} else if ("xlsx".equals(extension)) {
			return getExcel2007RowNum(file);
		} else {
			throw new Exception("文件类型不支持!");
		}
	}

	public static void main(String[] args) throws Exception {
//		ExcelUtil u = new ExcelUtil();
		File file = new File("C:\\Users\\Ray\\Desktop\\03_MARKET_CMPGN_CUST_TRG.xls");
		FileInputStream fs = new FileInputStream(file);
		// System.out.println(u.readExcel(fs,
		// "03_MARKET_CMPGN_CUST_TRG.xls","market"));
		System.out.println(read2003ExcelJXL(fs, 6));
		// readExcel(f2003, 6);
		// File f2007 = new File("D://营销活动目标客户模版.xls");
		// System.out.println(readExcel(f2007, 5));
		// List<List<Object>> list1 = readExcel(f2007, 4);
		//
		// List<List<Object>> listerror=new LinkedList<List<Object>>();
		// for (int i = 0; i < list1.size(); i++) {
		// if (list1.get(i).get(0).toString().equals("error")) {
		//
		// listerror.add(list1.get(i));
		// list1.remove(i);
		// i--;
		// }
		// }
		//
		// System.out.println(listerror);

		/*
		 * System.out.println(ExcelUtil.getExcel2007ValidRowNum(f2007, 0));
		 * List<Object> list2 = new ArrayList<Object>(); for (int i = 0; i <
		 * list1.size(); i++) { list2 = list1.get(i); //
		 * System.out.println(list2); StringBuffer bf = new StringBuffer(); if
		 * (StringUtils.isNotEmpty(list2.get(0).toString())) { String custName =
		 * list2.get(0).toString(); bf.append(custName + " "); } if
		 * (StringUtils.isNotEmpty(list2.get(1).toString())) { String brand =
		 * list2.get(1).toString(); bf.append(brand + " "); } if
		 * (StringUtils.isNotEmpty(list2.get(2).toString())) { String level =
		 * list2.get(2).toString(); bf.append(level + " "); } if
		 * (StringUtils.isNotEmpty(list2.get(3).toString())) { String address =
		 * list2.get(3).toString(); bf.append(address + " "); } if
		 * (StringUtils.isNotEmpty(list2.get(4).toString())) { String phoneNum =
		 * list2.get(4).toString(); bf.append(phoneNum + " "); } if
		 * (StringUtils.isNotEmpty(list2.get(5).toString())) { String address =
		 * list2.get(5).toString(); bf.append(address + " "); }
		 * 
		 * System.out.println(bf); }
		 */
	}

	public static File createTempFile(InputStream is, String extName) throws Exception {
		// 创建临时文件，用于解压
		File file = File.createTempFile("TempFile_", "." + extName);
		FileOutputStream os = new FileOutputStream(file);
		try {
			byte[] bytes = new byte[2048];
			int read;
			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return file;
	}

	public List<List<Object>> read2003ExcelForO2O(InputStream inputStream, String fileName) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		reading2003total = sheet.getPhysicalNumberOfRows();
		// System.out.println("读取 office 2003 excel 内容如下：");
		for (int i = 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row != null) {
				reading2003Row = (i + 1);
				List<Object> linked = new LinkedList<Object>();
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum() - 1; j++) {

					try {
						cell = row.getCell(j);
						if (cell != null && !cell.toString().trim().equals("")) {
							DecimalFormat df = new DecimalFormat("0");// 格式化
																		// number
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
							DecimalFormat nf = new DecimalFormat("0");// 格式化数字
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								if ("@".equals(cell.getCellStyle().getDataFormatString())) {
									value = df.format(cell.getNumericCellValue());
								} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
									value = nf.format(cell.getNumericCellValue());
								} else {
									value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								}
								break;
							case XSSFCell.CELL_TYPE_BOOLEAN:
								value = cell.getBooleanCellValue();
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								value = "";
								break;
							default:
								value = cell.toString();
							}
							linked.add(value);
						} else {
							linked.clear();
							linked.add("error");
							linked.add(fileName + "");
							linked.add((i + 1) + "行" + (j + 1) + "列不能为空");
							linked.add("含有空字段");
							break;
						}
					} catch (Exception e) {
						linked.clear();
						linked.add("error");
						linked.add(fileName);
						linked.add((i + 1) + "行" + (j + 1) + "列解析异常");
						linked.add("解析异常");
						break;
					}
				}
				list.add(linked);
			}
		}
		inputStream.close();
		return list;

	}

	public boolean match(String a) {
		String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(a);

		return m.find();// boolean

	}

	public static boolean isMobileNumber(String mobiles) {
		return Pattern.compile("^1\\d{10}").matcher(mobiles).matches();
	}

	public static boolean isMobileNumberNew(String mobiles) {
		return Pattern.compile("^((13[0-9])|(14[0-9])|(17[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}").matcher(mobiles)
				.matches();
	}

	public static boolean isPersonNames(String mobiles) {
		return Pattern.compile("[\u4E00-\u9FA5]{2,4}").matcher(mobiles).matches();
	}

	public static boolean isStaffNums(String mobiles) {
		return Pattern.compile("^[A-Za-z0-9]+$").matcher(mobiles).matches();
	}

	public static boolean isIDCardNumber(String idCards) {
		return Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9X])").matcher(idCards).matches();
	}

	public static boolean isEmailAddress(String emails) {
		return Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
				.matcher(emails).matches();
	}

	/**
	 * 解析敏客户信息导入XLS
	 */
	public List<Map<String, String>> readExcelBySnstvCust(InputStream inputStream, String fileName) throws IOException {
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003ExcelBySnstvCust(inputStream, fileName);
		} /*
			 * else if ("xlsx".equals(extension)) { return
			 * read2007ExcelBySnstvCust(inputStream,fileName); }
			 */ else {
			throw new IOException("不支持的文件类型!");
		}
	}

	/**
	 * 解析敏客户信息导入
	 */
	private List<Map<String, String>> read2003ExcelBySnstvCust(InputStream inputStream, String fileName)
			throws IOException {
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		reading2003total = sheet.getPhysicalNumberOfRows();
		for (int i = 5; i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row != null) {
				reading2003Row = (i + 1);
				Map<String, String> linked = new HashMap<String, String>();
				for (int j = 0; j <= row.getLastCellNum() - 1; j++) {
					try {
						cell = row.getCell(j);
						if (cell != null && !cell.toString().trim().equals("")) {
							DecimalFormat df = new DecimalFormat("0");// 格式化
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
							DecimalFormat nf = new DecimalFormat("0");// 格式化数字
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								if ("@".equals(cell.getCellStyle().getDataFormatString())) {
									value = df.format(cell.getNumericCellValue());
								} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
									value = nf.format(cell.getNumericCellValue());
								} else {
									value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
								}
								break;
							case XSSFCell.CELL_TYPE_BOOLEAN:
								value = cell.getBooleanCellValue();
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								value = "";
								break;
							default:
								value = cell.toString();
							}
							linked.put("flag", "true");
							if (j == 0) { // 判断手机号码是否正确
								if (!isMobileNumber(value.toString())) {
									linked.clear();
									linked.put("flag", "false");
									linked.put("row", (i + 1) + "");
									linked.put("FILE_NM", fileName);
									linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "列手机格式错误");
									linked.put("ERROR_DESC", "手机格式错误");
									break;
								}
							}
							if (j == 1) { // 判断名单类型是否正确
								if (!isRightCustType(value.toString())) {
									linked.clear();
									linked.put("flag", "false");
									linked.put("row", (i + 1) + "");
									linked.put("FILE_NM", fileName);
									linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "列手机格式错误");
									linked.put("ERROR_DESC", "名单类型错误");
									break;
								}
							}
							if (j == 0) {// 客户电话
								linked.put("telNum", value.toString());
							} else if (j == 1) {// 名单类型
								String custType = value.toString();
								if (custType.equals("红名单")) {
									custType = "01";
								} else if (custType.equals("白名单")) {
									custType = "02";
								} else if (custType.equals("黑名单")) {
									custType = "03";
								}
								linked.put("snstvCustTypeCd", custType);
							} else if (j == 2) {// 省
								linked.put("provCoNm", value.toString());
							} else if (j == 3) {// 市
								linked.put("embrCoNm", value.toString());
							} else if (j == 4) {// 品牌
								linked.put("brandNm", value.toString());
							}
						} else {
							linked.clear();
							linked.put("flag", "false");
							linked.put("FILE_NM", fileName);
							linked.put("row", (i + 1) + "");
							linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "列不能为空");
							linked.put("ERROR_DESC", "有字段为空");
							break;
						}
					} catch (Exception e) {
						linked.clear();
						linked.put("flag", "false");
						linked.put("row", (i + 1) + "");
						linked.put("FILE_NM", fileName);
						linked.put("ERROR_RSN_DESC", (i + 1) + "行" + (j + 1) + "解析异常");
						linked.put("ERROR_DESC", "解析异常");
						break;
					}
				}

				list.add(linked);
			}
		}
		inputStream.close();
		return list;
	}

	/**
	 * 敏感客户导入时，判断操作员填写的名单类型是否正确
	 */
	public static boolean isRightCustType(String custTypeCd) {
		if (custTypeCd.equals("白名单") || custTypeCd.equals("黑名单") || custTypeCd.equals("红名单")) {
			return true;
		}
		return false;
	}

	/**
	 * 双11——中奖信息Excel读取
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @author Van_Q 2015年10月29日
	 */
	public static List<Map<String, String>> read2007ExcelForWinningSMS(InputStream inputStream) throws IOException {
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			Map<String, String> linked = new HashMap<String, String>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum() - 1; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0");// 格式化数字
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				if (j == 2) {// 手机号
					linked.put("RECV_PHONE_NO", value.toString());
				} else if (j == 3) {// 短信内容
					linked.put("SMS_CNTT", value.toString());
				}
			}
			list.add(linked);
		}
		return list;
	}

	/**
	 * 获取excel文件内容，使用getExcelContentAsArray替换
	 * 
	 * @param fileStream
	 * @param beginRow
	 *            有效内容开始行数
	 * @return 二维字符串数组
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, String>> read2003ExcelJXL(InputStream fileStream, int beginRow)
			throws Exception {
		String[][] excelFileContent = null;
		if (beginRow < 1) {
			beginRow = 1;
		}
		WorkbookSettings wbSetting = new WorkbookSettings();
		wbSetting.setEncoding("GBK");
		Workbook workbook = Workbook.getWorkbook(fileStream, wbSetting);
		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		List rowList = null;
		if (rows >= beginRow) {
			rowList = new ArrayList();
			String[] rowContent = null;
			for (int i = (beginRow - 1); i < rows; i++) {
				Cell[] cells = sheet.getRow(i);
				rowContent = new String[cells.length];
				for (int j = 0; j < cells.length; j++) {
					Cell cell = cells[j];
					String tmpContent = cell.getContents();
					if (cell.getType() == CellType.DATE || cell.getType() == CellType.DATE_FORMULA) {
						tmpContent = cvtDateString(tmpContent, "dd/MM/yyyy", "yyyy-MM-dd");
					} else if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
						// 对数字型进行特殊处理，滤除掉","
						if (tmpContent.indexOf(",") >= 0) {
							tmpContent = tmpContent.replaceAll(",", "");
						}
					}
					if (!StringUtils.isEmpty(tmpContent)) {
						tmpContent = tmpContent.trim();
					}
					rowContent[j] = tmpContent;
				}
				rowList.add(rowContent);
			}

			List removeList = new ArrayList(); // 删除空行
			for (int i = 0; i < rowList.size(); i++) {
				boolean allEmpty = true;
				String[] sArray = (String[]) rowList.get(i);
				for (int j = 0; j < sArray.length; j++) {
					if (StringUtils.isNotEmpty(sArray[j])) {
						allEmpty = false;
						break;
					}
				}
				if (allEmpty) {
					removeList.add(sArray);
				}
			}
			rowList.removeAll(removeList);

			excelFileContent = new String[rowList.size()][];
			for (int i = 0; i < rowList.size(); i++) {
				String[] lineAry = (String[]) rowList.get(i);
				excelFileContent[i] = lineAry;
			}
		} else {
			return null;
		}

		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		for (int i = 0; i < excelFileContent.length; i++) {
			Map<String, String> linked = new HashMap<String, String>();
			String name = excelFileContent[i][0];
			String phone = excelFileContent[i][1];
			linked.put("LIN_CUST_NM", name);
			linked.put("RESV_MOBNUM", phone);
			linked.put("flag", "true");
			list.add(linked);
		}
		return list;
	}

	public static String cvtDateString(String strDate, String strOldFormat, String strNewFormat) {
		try {
			SimpleDateFormat sdfOld = new SimpleDateFormat(strOldFormat);
			SimpleDateFormat sdfNew = new SimpleDateFormat(strNewFormat);
			Date date = sdfOld.parse(strDate);
			return sdfNew.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

}