package com.pujjr.base.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.HolidayMapper;
import com.pujjr.base.domain.Holiday;
import com.pujjr.base.service.IHolidayService;
import com.pujjr.base.vo.HolidayAddVo;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
public class HolidayServiceImpl implements IHolidayService {

	@Autowired
	private HolidayMapper holidayDao;

	@Override
	public List<Holiday> getHolidayList() {
		// TODO Auto-generated method stub
		return holidayDao.selectAll();
	}

	@Override
	public Holiday getHolidayByDate(String id) {
		// TODO Auto-generated method stub
		return holidayDao.selectByPrimaryKey(id);
	}

	@Override
	public void addHoliday(Date holidayStartDate,Date holidayEndDate,String holidayDesc,String createId) throws Exception {
		// TODO Auto-generated method stub
		int spaceDay = Utils.getSpaceDay(holidayStartDate, holidayEndDate);
		if(spaceDay<0)
		{
			throw new Exception("结束日期小于开始日期");
		}
		holidayDao.deleteHoliday(holidayStartDate, holidayEndDate);
		while(Utils.getSpaceDay(holidayStartDate, holidayEndDate)>=0)
		{
			Holiday holiday = new Holiday();
			holiday.setId(Utils.get16UUID());
			holiday.setHolidayDate(holidayStartDate);
			holiday.setHolidayDesc(holidayDesc);
			holiday.setCreateId(createId);
			holiday.setCreateTime(new Date());
			holidayDao.insert(holiday);
			holidayStartDate = Utils.getDateAfterDay(holidayStartDate, 1);
		}
	}

	@Override
	public void modifyHoliday(Holiday record) {
		// TODO Auto-generated method stub
		holidayDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteHolidayById(String id) {
		// TODO Auto-generated method stub
		holidayDao.deleteByPrimaryKey(id);
	}

	@Override
	public Date getWorkDate(Date date) {
		// TODO Auto-generated method stub
		Holiday holiday = holidayDao.seleteByHolidayDate(date);
		//如果当前日期不是节假日则工作日为当前日
		if(holiday==null)
		{
			return date;
		}
		else
		{
			//如果不是则循环判定后续日期是否节假日
			while(true)
			{
				date  = Utils.getDateAfterDay(date, 1);
				if(holidayDao.seleteByHolidayDate(date)==null)
				{
					break;
				}
			}
			return date;
		}
	}

	@Override
	public void initHoliday(String year,String operId) throws ParseException {
		// TODO Auto-generated method stub
		Date startDate = Utils.str82date(year+"0101");
		Date endDate = Utils.str82date(year+"1231");
		//先删除目前的节假日设置
		holidayDao.deleteHoliday(startDate, endDate);
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(startDate);
		while(true)
		{
		     int w = cal.get(Calendar.DAY_OF_WEEK);
		     if(w==1||w==7)
		     {
		    	 Holiday holiday = new Holiday();
		    	 holiday.setId(Utils.get16UUID());
		    	 holiday.setHolidayDate(cal.getTime());
		    	 holiday.setHolidayDesc("周末");
		    	 holiday.setCreateId(operId);
		    	 holiday.setCreateTime(new Date());
		    	 holidayDao.insert(holiday);
		     }
		     cal.add(Calendar.DATE, 1);
		     if(Utils.compareDateTime(endDate, cal.getTime())>0)
		     {
		    	 break;
		     }
		     
		}
	}

}
