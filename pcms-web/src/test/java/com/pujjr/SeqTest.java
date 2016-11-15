package com.pujjr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pujjr.postloan.schedule.ScheduleService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/spring*.xml"})
public class SeqTest 
{
	@Test
	public void testCutOff() throws ParseException
	{
		ScheduleService schedule = new ScheduleService();
		schedule.dayJob();
	}
}
