package cn.itrip.auth.service;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.itrip.common.SystemConfig;


@Service("smsService")
public class SmsServiceImpl implements SmsService {
	@Override
	public void send(String to, String templateId, String[] datas) throws Exception {
	}
}
