package com.dadou.sys.log.action;

import com.dadou.sys.CmsConst;
import com.dadou.sys.log.pojos.SysLog;
import com.dadou.sys.log.service.LogService;
import com.framework.core.page.Pagination;
import com.framework.core.utils.ActionContextUtils;
import com.framework.core.utils.DateUtils;
import com.framework.core.utils.JsonUtils;
import com.framework.core.utils.StringUtils;
import com.framework.core.web.controller.BaseController;
import com.framework.core.web.ui.OptionsString;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志操作类,负责处理系统日志的CRUD操作 例如:uisb的日志管理、网校的日志管理等等
 */
@Controller
@RequestMapping(value="/log")
public class LogAction extends BaseController {
    /**
     * 日志业务类
     */
	@Resource(name="sys_logService")
    private LogService logService;

    /**
     * 模块列表
     */
    private List<OptionsString> moduleList = new ArrayList<OptionsString>();

    // ///////////////////////////////////////
    // //初始化操作
    // //////////////////////////////////////
    private void setModuleListValues() {
        String[] days = DateUtils.dayInWeek();

//        // 设置开始时间和结束时间
//        if (StringUtils.isEmpty(this.beginDate)) {
//            this.beginDate = days[0].substring(0, 4) + "-"
//                    + days[0].substring(4, 6) + "-" + days[0].substring(6, 8);
//        }
//        if (StringUtils.isEmpty(this.endDate)) {
//            this.endDate = days[1].substring(0, 4) + "-"
//                    + days[1].substring(4, 6) + "-" + days[1].substring(6, 8);
//        }
    }
    /**
     * 系统日志列表
     */
    @RequestMapping(value="/list")
    public String list(SysLog log) {
        setModuleListValues();
        // 从请求参数中获取pageNumber
        String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER);
        int pageNo = NumberUtils.toInt(str[0], 1);
        Pagination<SysLog> pagination = logService.findByPage(pageNo,
                this.getPageSize(), getCriteria(log));
        // 把pagination对象保存到request范围中
        ActionContextUtils.setAtrributeToRequest("pagination", pagination);
        return "list";
    }


    /**
     * 系统日志Ajax列表
     */
    @RequestMapping(value="/listAjax")
    public String listAjax(SysLog log) {
		// 从请求参数中获取pageNumber
		String[] str = ActionContextUtils.getParameters(CmsConst.PAGE_NUMBER_E);
		int pageNo = NumberUtils.toInt(str[0], 1);

		Pagination<SysLog> pagination = this.logService.findByPage(
				pageNo, this.getPageSize4E(), getCriteria(log));
		// 把pagination对象保存到request范围中
		ActionContextUtils.setAtrributeToRequest("pagination", pagination);
		Map<String, Object> map = new HashMap<String, Object>();
        map.put(TOTAL, pagination.getMaxElements());
        map.put(ROWS, pagination.getList());
		String jsonData = JsonUtils.toJsonIncludeNull(map);
		return jsonData;
    }

    /**
     * 获取条件集合
     * 
     * @return
     */
    public Map<String, Object> getCriteria(SysLog log) {
    	Map<String, Object> queryMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(log.getUserName())) {
        	queryMap.put("userName", "%"+log.getUserName()+"%");
        }
//        if (this.level != -1) {
//        	queryMap.put("level", this.level);
//        }
//        if (StringUtils.isNotEmpty(this.Ip)) {
//        	queryMap.put("ip", "%"+this.Ip+"%");
//        }
//        // 开始时间最小值
//        Date beginDate = DateUtils.toDate("yyyyMMddHHmmSS", this.beginDate
//                .replaceAll("-", "")
//                + "000000");
//        queryMap.put("beginDate", DateUtils.formatDate("yyyy-MM-dd HH:mm:SS", beginDate));
//        // 结束时间最大值
//        Date endDate = DateUtils.toDate("yyyyMMddHHmmSS", this.endDate
//                .replaceAll("-", "")
//                + "235959");
//        queryMap.put("endDate", DateUtils.formatDate("yyyy-MM-dd HH:mm:SS", endDate));
        queryMap.put(ORDER, this.getOrder());
        queryMap.put(SORT, this.getSort());
        return queryMap;
    }
}
