import java.text.DecimalFormat;

public class Programmer extends Worker {
	public String language;
	public String type;

	public Programmer() {
	}

	// Programmer类的初始化
	public Programmer(String name, int age, int salary, String language,
					  String type) throws IllegalArgumentException{
		super(name,age,salary,"Programmer");
		this.language=language;
		this.type=type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// 按照规则计算当月的奖金
	public String getBonus(int overtime) throws IllegalArgumentException{
		if(overtime<0){
			throw new IllegalArgumentException("Overtime illegal!");
		}
		double bonus=0;
		if(type.equals("Develop")){
			bonus=salary*0.2;
			if(overtime>5){
				bonus+=500;
			}
			else{
				bonus+=(overtime*100);
			}
		}
		else if(type.equals("Test")){
			bonus=salary*0.15;
			if(overtime>6){
				bonus+=1000;
			}
			else{
				bonus+=(overtime*150);
			}
		}
		else if(type.equals("UI")){
			bonus=salary*0.25;
			if(overtime>6){
				bonus+=300;
			}
			else{
				bonus+=(50*overtime);
			}
		}
		DecimalFormat df=new DecimalFormat("#0.000");
		String format_bonus=df.format(bonus);
		if(format_bonus.charAt(format_bonus.length()-1)>='5'){
			char next= (char) (format_bonus.charAt(format_bonus.length()-2)+1);
			format_bonus=format_bonus.substring(0,format_bonus.length()-2)+next;
		}
		else {
			format_bonus=format_bonus.substring(0,format_bonus.length()-1);
		}
		String prefix=format_bonus.substring(0,format_bonus.length()-3);
		String result="";
		int times=0;
		for(int i=prefix.length()-1;i>=0;i--){
			times++;
			result=prefix.charAt(i)+result;
			if(times%3==0){
				result=","+result;
			}
		}

		if(result.charAt(0)==','){
			result=result.substring(1);
		}

		result=result+"."+format_bonus.substring(format_bonus.length()-2);
		return result;
	}

	// 展示基本信息
	public String show() {
		return "My name is "+name+" ; age : "+age+" ; language : "+language+" ; salary : "+salary+".";
	}


	/**
	 * 信息隐藏
	 *
	 * 为了保护用户的隐私，系统需要将用户号码和邮箱隐藏起来。 对输入的邮箱或电话号码进行加密。 commnet
	 * 可能是一个邮箱地址，也可能是一个电话号码。
	 *
	 * 1. 电子邮箱 邮箱格式为 str1@str2
	 * 电子邮箱的名称str1是一个长度大于2.并且仅仅包含大小写字母和数字的字符串，名称str1后紧接符号@
	 * 最后是邮箱所在的服务器str2,str2中可能包含多个. 如qq.com smail.nju.edu.cn等 邮箱地址是有效的，一个正确的示例为:
	 * str1@smail.nju.edu.cn 为了隐藏电子邮箱，所有的str1和str2中的字母必须被转换成小写的，
	 * 并且名称str1的第一个字和最后一个字的中间的所有字由 5 个 '*' 代替。 如果邮箱中含有非法字符或格式不正确，则返回illegal
	 *
	 * 示例：
	 *
	 * comment: "Qm@Qq.com"
	 *
	 * return: "q*****m@qq.com"
	 *
	 * 2. 电话号码 电话号码是一串包括数字 0-9，以及 {'+', '-', '(', ')', ' '} 这几个字符的字符串。
	 * 你可以假设电话号码包含 10 到 13 个数字。 电话号码的最后 10 个数字组成本地号码，在这之前的数字组成国际号码。
	 * 国际号码是可选的。我们只暴露最后 4 个数字并隐藏所有其他数字。 本地号码有格式，并且如 "***-***-1111" 这样显示，
	 * 为了隐藏有国际号码的电话号码，像 "+111 111 111 1111"，我们以 "+***-***-***-1111"
	 * 的格式来显示。在本地号码前面的 '+' 号 和第一个 '-' 号仅当电话号码中包含国际号码时存在。 例如，一个 12 位的电话号码应当以
	 * "+**-" 开头进行显示。 注意：像 "("，")"，" " 这样的不相干的字符以及不符合上述格式的额外的减号或者加号都应当被删除。
	 * 示例1:
	 *
	 * comment: "1(234)567-890"
	 *
	 * return: "***-***-7890"
	 *
	 * 示例2:
	 *
	 * comment: "86-(10)12345678"
	 *
	 * return: "+**-***-***-5678"
	 *
	 * @param comment
	 */
	public String hideUserinfo(String comment) {
		//email
		if(comment.indexOf("@")!=-1){
			String str1=comment.substring(0,comment.indexOf("@"));
			String str2=comment.substring(comment.indexOf("@")+1);
			for(int i=0;i<str1.length();i++){
				char c=str1.charAt(i);
				if((c>='0' && c<='9')||(c>='a' && c<='z')||(c>='A' && c<='Z')){
					continue;
				}
				else{
					return "illegal";
				}
			}
			str1=str1.toLowerCase();
			str2=str2.toLowerCase();
			return str1.charAt(0)+"*****"+str1.charAt(str1.length()-1)+"@"+str2;
		}
		//phone
		else{
			String phone="";
			String result="";
			for(int i=0;i<comment.length();i++){
				if(comment.charAt(i)<='9'&&comment.charAt(i)>='0'){
					phone+=comment.charAt(i);
				}
			}
			if(phone.length()<10){
				result="illegal";
			}
			else if(phone.length()==10){
				result="***-***-"+phone.substring(6);
			}
			else{
				result="+";
				for(int i=0;i<phone.length()-10;i++){
					result+="*";
				}
				result=result+"-***-***-"+phone.substring(phone.length()-4);
			}
			return result;
		}
	}

}
