package com.example.demo.common.enums;

/**
 * 异常枚举
 * 
 * @author guoyanyong
 * @email 
 * @time 
 */
public enum ErrorMsg {
	/** ------@Valid 输入参数绑定错误 ----- */
	SystemException(999,"系统异常，请稍后重试"),
	ApiDeprecated(998,"版本已过期，请升级最新版本后使用！"),
	TokenIsInvalid(1000,"非法访问"),
	TokenIsNotComplete(1000,"Token参数不完整"),
	BindingResultError(1001, "数据绑定错误"),
	SendSmsError(1002, "短信异常，请退出重试或联系客服！"),
	TokenIsExpired(1003,"token已过期"),
	TokenInsufficientPermissions(1004,"访问权限不足"),
	InvalidDateTime(1005, "无效的时间"),
	APPIDIsInvalid(1006,"无效的APPID"),
	InvalidRequestParams(1007, "访问参数错误！"),
	DuplicateRequest(1008, "重复访问错误，请稍后再试！"),
	PasswordError(2005,"密码错误"),
	InvalidMobile(2006, "无效的手机号"),
	InvalidVerifyCode(2007, "验证码无效"),
	AccountNotExistOrDisable(2008, "账号不存在"),
	MobileExists(2009, "手机号已被使用"),
	BindedOnOtherOpenid(2010,"该手机号已经被其它微信用户绑定"),
	invaildUrl(2011, "无效的URL"),
	invalidOpenid(2012, "无效的openid"),
	MobileNoRegist(2013, "该手机号尚未注册"),
	AccountOrPasswordInvalid(2014, "账号或密码错误"),
	MobileHaveRegist(2015, "该手机号已注册"),
	PasswordInvalid(2016, "密码校验错误"),
	MemberBelongWithDoctor(2017,"该用户已经是您的患者了，请不要重复添加！"),
	ConfirmPasswordError(2018, "两次输入密码不一致"),
	PasswordUnmatched(2019, "设置的密码不符合规范"),
	ApplyExit(2022, "您的请求已发送，正在等待对方同意！"),
	PasswordHadSet(2023, "密码已设置，请勿重复设置！"),
	DoctorAccountDisable(2024, "您的账号已被禁用！"),
	SmsSendFrequent(2025, "验证码已发送，请查收手机短信！"),//"验证码已发送，10分钟之内不要频繁操作哟，请查收手机短信！"),
	LengthOver(2026, "超过限制"),
	MemberBelongWithFamily(2027, "该用户已是您的家人"),
	SmsFrequencyLimitReaches(2028, "您今天获取登录验证码的次数已达到上限，明天再试吧！"),//当天发送短信验证码已经达到上限
	NotOperateOwnAccount(2029, "不能添加自己的账户哦！"),
	OnlySetOwnFamilyMemberToDefault(2030, "只能设置自己家庭下的成员为默认"),
	PasswordLengthNot(2031,"密码长度不在6-15位"),
	DoctorCertifictePicError(2032, "必须上传资质认证图片"),
	IDCardInvalid(2033, "身份证号码无效"),
	UploadFileIsNotNull(2034,"请指定您要上传的文件"),
	MsgNotExit(2035,"消息不存在"),
	HadSigned(2036, "已签约，请勿重复申请！"),
	AccountNotMatch(2037, "请求的账号信息不匹配！"),
	MedicalServiceNotAllowFinish(2038, "您还没有写总结，不能结束服务"),
	PasswordNotNull(2039,"密码不能为空"),
	YouBelongWithFamily(2040,"您已经属于%s的家庭组，无需重复添加"),
	MemberIsRequestYou(2041,"%s已经向您发送了添加请求，请关注并处理"),
	FieldNotBiggerThan(2042,"%s不能超过%s字符!"),
	DrugFieldNotBiggerThan(2043,"第%s条用药指导，%s不能超过%s字符!"),
	DoctorBelongOrg(2044,"该医生已是本机构的医生，不需要重复添加"),
	TopicIsDeleted(2045,"该话题已被删除"),
	FriendApplyForbidden(2046, "您不能添加自己"),
	DoctorAddFriendFailWhenNotCertificate(2047, "该医生尚未通过认证，只允许添加认证通过的医生!"),
	DoctorBelongOtherOrg(2048,"添加失败，该医生已绑定其他机构"),
	QuantityLeftLessThanZero(2049, "您所够买的数量已经超过库存数量，请电话联系平台"),
	OrganizatioStatusFreeze(2055,"该机构账号已被冻结"),
	GET_ALIPAY_PARAMSE_RROR(2060,"获取支付宝参数错误"),
	ORDER_IS_NOT_FOUND(2065,"订单不存在"),
	NotResetPassword(2068,"已经重置密码,无需重置"),
	PLEASE_CHOOSE_PAY_CHANNEL(2070,"请选择支付渠道"),
	DoctorNotCompleteIfo(2071,"添加失败，因该医生未完善信息，无法进行机构绑定。"),
	InvalidBloodDevice(2075,"该设备暂未收录，请联系客服处理！"),
	OverTheMaxBingding(2076,"同一设备只能绑定%s用户！"),
	HadBindingThisDevice(2077, "您已成功绑定该设备，不要重复绑定！"),
	HadBindingOtherDevice(2078, "您已成功绑定其它设备，不要重复绑定！"),
	THE_TOPIC_IS_FAVORITED(2080, "该话题已经收藏过了，请忽重复收藏！"),
	INVITE_ALREADY_SEND(2081, "邀请已发送，不需要重复邀请"),
	ANOT_AGAIN_SENG(2082,"添加申请已发出,不需要重复邀请"),
	WAIT_MEMBER_AGREE(2083,"添加申请已发出,请等待患者同意"),
	NOTIFY_ALREADY_DONE(2084,"该邀请已%s");



	public int state;
	public String msg;

	ErrorMsg(int state, String msg) {
		this.state = state;
		this.msg = msg;
	}
}
