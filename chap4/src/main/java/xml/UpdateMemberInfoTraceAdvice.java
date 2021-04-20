package xml;

public class UpdateMemberInfoTraceAdvice {
	//arg-names="ret,id,info"
	//result : 핵심메서드의 리턴값
	//id : String
	//info : UpdateInfo
	public void traceReturn(Object result, String memberid, UpdateInfo info) {
		System.out.println("[TA] 정보 수정 : 결과="+result+
				", 대상회원="+memberid+", 수정정보="+info);
	}
}
