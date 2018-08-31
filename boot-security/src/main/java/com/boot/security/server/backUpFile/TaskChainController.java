/*package com.boot.security.server.backUpFile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 参数作为连接的使用方法   较推荐
 * 
@RestController
@RequestMapping(value = "/task")
public class TaskChainController {

	

	@RequestMapping(value = "/{taskId}/data/chain", method = RequestMethod.GET)
	@ApiOperation(value = "任务数据链", notes = "数据链", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskId", value = "任务ID", required = true, dataType = "long", paramType = "path") })
	public ResultBody<List<BussCode2TaskDataChain>> dataSourceChain(@PathVariable("taskId") Long taskId) {
		return new ResultBody<List<BussCode2TaskDataChain>>(taskChainService.dataSourceChain(taskId));
	}

}
*/