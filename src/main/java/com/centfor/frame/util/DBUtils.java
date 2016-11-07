package com.centfor.frame.util;

import java.io.File;
import java.util.Date;

public class DBUtils {
 private String backupcmd;
 private String backupfilepath;
 private String restorecmd;
public String getBackupcmd() {
	return backupcmd;
}
public void setBackupcmd(String backupcmd) {
	this.backupcmd = backupcmd;
}
public String getBackupfilepath() {
	return backupfilepath;
}
public void setBackupfilepath(String backupfilepath) {
	this.backupfilepath = backupfilepath;
}
public String getRestorecmd() {
	return restorecmd;
}
public void setRestorecmd(String restorecmd) {
	this.restorecmd = restorecmd;
}

public File backupFile() throws Exception{
	Date now=new Date();
	String s=DateUtils.convertDate2String("yyyy-MM-dd_HH-mm-ss_SSS", now);
	String filepath=backupfilepath+s+".sql";
	String cmd=backupcmd+filepath;
	Runtime rt = Runtime.getRuntime();    
	Process proc = rt.exec(cmd);
	if(proc.waitFor()==0){
      File f=new File(filepath);
      return f;
	}else{
		return null;
	}
	
}

	
}
