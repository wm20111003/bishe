package com.centfor.school.entity;

public class UploadFilePathVO {
	private String realPath;
    private String relativePath;
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((realPath == null) ? 0 : realPath.hashCode());
		result = prime * result + ((relativePath == null) ? 0 : relativePath.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadFilePathVO other = (UploadFilePathVO) obj;
		if (realPath == null) {
			if (other.realPath != null)
				return false;
		} else if (!realPath.equals(other.realPath))
			return false;
		if (relativePath == null) {
			if (other.relativePath != null)
				return false;
		} else if (!relativePath.equals(other.relativePath))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UploadFilePathVO [realPath=" + realPath + ", relativePath=" + relativePath + "]";
	}

}
