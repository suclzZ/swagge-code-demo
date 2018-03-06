package com.swagger.rest;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.model.JsonResult;
import com.swagger.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="/user",tags="UserController")
@RestController
@RequestMapping("/user")
public class UserController {

	public static Map<Integer,User> users = Collections.synchronizedMap(new HashMap<Integer, User>());
	
	static{
		buildUsers();
	}
	
	private static void buildUsers() {
		for(int i=0;i<10;i++) {
			User user = new User();
			user.setId(i);
			user.setName("name"+i);
			user.setCtm(new Date());
			user.setAge((int) (20+(Math.random()*10)/2));
			users.put(i, user);
		}
	}
	
	@ApiOperation(value="获取用户信息",notes="根据id查询用户")
	@ApiImplicitParams({@ApiImplicitParam(name="id",value="用户id",required=true,dataType="Integer",paramType="path")})
	@GetMapping("/{id}")
	public ResponseEntity<JsonResult> getUserById(@PathVariable("id") int id){
		User user = users.get(id);
		JsonResult jr = new JsonResult();
		jr.setResult(user);
		jr.setStatus("ok");
		return ResponseEntity.ok(jr);
	}

	@ApiOperation(value="获取所有用户",notes="获取所有用户")
	@GetMapping("")
	public ResponseEntity<JsonResult> getUsers(){
		JsonResult jr = new JsonResult();
		jr.setResult(users);
		jr.setStatus("ok");
		return ResponseEntity.ok(jr);
	}
	
	@ApiOperation(value="新增用户",notes="新增用户")
	@ApiImplicitParam(name="user",value="用户",required=true,dataType="User",paramType="body")
	@PostMapping("")
	public ResponseEntity<JsonResult> saveUser(@RequestBody User user){
		users.put(user.getId(), user);
		JsonResult jr = new JsonResult();
		jr.setResult(user);
		jr.setStatus("ok");
		
		return ResponseEntity.ok(jr);
	}
	
	@ApiOperation(value="修改用户",notes="根据id修改用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id",value="用户id",required=true,dataType="Integer",paramType="path"),
		@ApiImplicitParam(name="user",value="用户",required=true,dataType="User",paramType="body")
	})
	@PutMapping("/{id}")
	public ResponseEntity<JsonResult> updateUserById(@PathVariable("id") int id,@RequestBody User user){
		User _user = users.get(id);
		users.put(id, user);
		JsonResult jr = new JsonResult();
		jr.setResult(_user);
		jr.setStatus("ok");
		return ResponseEntity.ok(jr);
	}
	
	@ApiOperation(value="删除用户信息",notes="根据id删除用户")
	@ApiImplicitParams({@ApiImplicitParam(name="id",value="用户id",required=true,dataType="Integer",paramType="path")})
	@DeleteMapping("/{id}")
	public ResponseEntity<JsonResult> deleteUser(@PathVariable("id") int id){
		User user = users.get(id);
		users.remove(id);
		JsonResult jr = new JsonResult();
		jr.setResult(user);
		jr.setStatus("ok");
		
		return ResponseEntity.ok(jr);
	}
	
	@ApiOperation(value="添加用户信息",notes="新增用户")
//	@ApiImplicitParam(name="user",value="用户",required=true,dataType="User",paramType="query")
	@PostMapping("/save")
	public ResponseEntity<JsonResult> save(User user){//@ModelAttribute
		users.put(user.getId(), user);
		JsonResult jr = new JsonResult();
		jr.setResult(users);
		jr.setStatus("ok");
		return ResponseEntity.ok(jr);
	}
	
	@InitBinder  
    public void InitBinder(ServletRequestDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));  
    }  
	
	@ApiOperation(value="用户信息",notes="获取用户")
	@ApiImplicitParams({@ApiImplicitParam(name="id",value="用户id",required=true,dataType="Integer",paramType="query")})
	@GetMapping("/get")
	public User get(@RequestParam("id") int id) {
		return users.get(id);
	}
	
	@ApiIgnore//忽略
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test() {
		return "test";
	}
}
