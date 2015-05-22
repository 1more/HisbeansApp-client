package ghost.android.hbapp.setting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeungMin
 * @email rfrost77@gmail.com
 * @classname SettingNoticeModel.java
 * @package ghost.android.hbapp.setting
 * @date 2012. 10. 23. 오후 8:04:24
 * @purpose : 공지사항 정보를 Server로부터 받아 저장할 Notice Model Class
 * @comment :
 */

public class SettingNoticeModel {
	private List<NoticeInfo> notices = new ArrayList<NoticeInfo>();
	
	/* constructor */
	public SettingNoticeModel() {
		super();
	}
	public SettingNoticeModel(List<NoticeInfo> notices) {
		super();
		this.notices = notices;
	}

	/* getter and setter */
	public List<NoticeInfo> getNotices() {
		return notices;
	}
	public void setNotices(List<NoticeInfo> notices) {
		this.notices = notices;
	}
	
	/* public methods */
	public int getSize(){
		return notices.size();
	}
	public NoticeInfo get(int i){
		return notices.get(i);
	}


	/* inner class for data */
	public static class NoticeInfo{
		private int docSrl;				// identifier for each notice
		private String title;				// title
		private String titleBold;	// true if title is bold-type character
		private String titleColor;		// color code for title
		private String content; 		// contents. it contains HTML tags like <b> or <h2>
		private int readedCount;	// read count
		private int votedCount;		// voted count
		private String regDate;			// registered date
		
		public NoticeInfo() {
			super();
		}
		public NoticeInfo(int docSrl, String title, String titleBold,
				String titleColor, String content, int readedCount,
				int votedCount, String regDate) {
			super();
			this.docSrl = docSrl;
			this.title = title;
			this.titleBold = titleBold;
			this.titleColor = titleColor;
			this.content = content;
			this.readedCount = readedCount;
			this.votedCount = votedCount;
			this.regDate = regDate;
		}
		
		public int getDocSrl() {
			return docSrl;
		}
		public void setDocSrl(int docSrl) {
			this.docSrl = docSrl;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getTitleBold() {
			return titleBold;
		}
		public void setTitleBold(String titleBold) {
			this.titleBold = titleBold;
		}
		public String getTitleColor() {
			return titleColor;
		}
		public void setTitleColor(String titleColor) {
			this.titleColor = titleColor;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getReadedCount() {
			return readedCount;
		}
		public void setReadedCount(int readedCount) {
			this.readedCount = readedCount;
		}
		public int getVotedCount() {
			return votedCount;
		}
		public void setVotedCount(int votedCount) {
			this.votedCount = votedCount;
		}
		public String getRegDate() {
			return regDate;
		}
		public void setRegDate(String regDate) {
			this.regDate = regDate;
		}
	}
}
