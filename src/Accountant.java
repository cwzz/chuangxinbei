import java.util.ArrayList;

public class Accountant extends Worker {
	public String password;

	public Accountant() {

	}
	
	//初始化Accountant
	public Accountant(String name, int age, int salary, String password) {
		super(name,age,salary,"Accountant");
		this.password=password;
	}

	/**
	 * 数字转换
	 * 随着公司业务的开展，国际性业务也有相应的拓宽，
	 * 会计们需要一个自动将数字转换为英文显示的功能。
	 * 编辑们希望有一种简约的方法能将数字直接转化为数字的英文显示。
	 *
	 * 给定一个非负整数型输入，将数字转化成对应的英文显示，省略介词and
	 * 正常输入为非负整数，且输入小于2^31-1;
	 * 如果有非法输入（字母，负数，范围溢出等），返回illegal
	 *
	 * 示例：
	 *
	 * number: 2132866842
	 * return: "Two Billion one Hundred Thirty Two Million Eight Hundred Sixty Six Thousand Eight Hundred Forty Two"
	 *
	 * number：-1
	 * return："illegal"
	 * @param number
	 */
	public  String numberToWords (String number){
		if(number.equals("0")){
			return "Zero";
		}

		//字母
		for(int i=0;i<number.length();i++){
			if(!(number.charAt(i)>='0'&&number.charAt(i)<='9')){
				return "illegal";
			}
		}

		try{
			int temp=Integer.valueOf(number);
			//负数
			if(temp<0){
				return "illegal";
			}else{
				String result=transToWords(temp);
				return result;
			}
		}catch (Exception e){
			//溢出
			return "illegal";
		}

	}


	public String transToWords(int num) {
		if(num == 0) {
			return "Zero";
		}
		String result = "";
		if(num > 999999999){
			result =result+transThreeInt(num/1000000000) + " Billion";
			num %= 1000000000;
		}
		if(num > 999999){
			result =result+ transThreeInt(num/1000000) + " Million";
			num %= 1000000;
		}
		if(num > 999){
			result =result+ transThreeInt(num/1000) + " Thousand";
			num %= 1000;
		}
		if(num > 0){
			result =result+ transThreeInt(num);
		}
		return result.trim();
	}



	private String transThreeInt(int num){
		String oneToNine[] = new String[]{ "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
		String tenToNineteen[] = new String[]{ "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
		String tenToNinety[] = new String[]{ "Ten","Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };

		String result = "";
		if(num > 99){
			result += " " + oneToNine[num / 100 - 1] + " Hundred";
		}
		num %= 100;
		if(num>19){
			result += " " + tenToNinety[num / 10 - 1];
			num %= 10;
		}else if(num>9){
			result += " " + tenToNineteen[num - 10];
			num = 0;
		}
		if(num > 0){
			result += " " + oneToNine[num-1];
		}
		return result;
	}
    
    /**
     * 检验密码
     * 由于会计身份的特殊性，对会计的密码安全有较高的要求，
     * 会计的密码需要由8-20位字符组成；
     * 至少包含一个小写字母，一个大写字母和一个数字，不允许出现特殊字符；
     * 同一字符不能连续出现三次 (比如 "...ccc..." 是不允许的, 但是 "...cc...c..." 可以)。
     * 
     * 如果密码符合要求，则返回0;
     * 如果密码不符合要求，则返回将该密码修改满足要求所需要的最小操作数n，插入、删除、修改均算一次操作。
     *
     * 示例：
     * 
     * password: HelloWorld6
     * return: 0
     *
     * password: HelloWorld
     * return: 1
     * 
     * @param
     */
	public  int checkPassword(){
		String pass = this.password;
		if (pass == null || pass.length() == 0) return 8;
		int len = pass.length();
		boolean hasBig=false, hasSmall=false, hasDigit=false;
		int havenot=3;
		int modify=0, insert=0, delete=0;
		ArrayList<String> errorStr = new ArrayList<>();
		char c;
		for (int i = 0; i < len; i++) {
			c = pass.charAt(i);
			//特殊字符
			if (!Character.isLetterOrDigit(c)) {
				errorStr.add(String.valueOf(c));
				continue;
			}
			if (!hasDigit && Character.isDigit(c)){
				havenot--;
				hasDigit=true;
			}
			if(!hasBig && Character.isUpperCase(c)){
				havenot--;
				hasBig=true;
			}
			if (!hasSmall&& Character.isLowerCase(c)){
				havenot--;
				hasSmall=true;
			}
			//连续字符串
			int j = i + 1;
			while (j < len) {
				if (pass.charAt(j) == c) {
					j++;
				} else {
					break;
				}
			}
			if (j - i >= 3) {//连续三个
				errorStr.add(pass.substring(i, j));
				i = j - 1;
			}
		}
		int tmpLen=len;
		int errorLen;
		for (String s:errorStr){
			errorLen=s.length();
			if(errorLen==0){//特殊字符
				if (tmpLen<=20){
					modify++;
				}else{
					tmpLen--;
					delete++;
				}
			}else{//重复字符串
				if (tmpLen<8){
					tmpLen += (errorLen/2);
					insert+=(errorLen/2);
				}else if (tmpLen>20){
					tmpLen-=(errorLen-2);
					delete+=(errorLen-2);
				}else{
					modify+=(errorLen/3);
				}
			}
		}
		if (tmpLen>20){
			delete+=(tmpLen-20);
		}
		if(tmpLen<8){
			insert+=(8-tmpLen);
		}
		if (insert+modify<2){
			modify+=havenot;
		}
		return insert+delete+modify;

	}
}
