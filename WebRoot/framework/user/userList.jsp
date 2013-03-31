<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/import.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<style>
		#fm {
		    margin: 0;
		    padding: 10px 30px;
		}
		.ftitle {
		    border-bottom: 1px solid #CCCCCC;
		    font-size: 14px;
		    font-weight: bold;
		    margin-bottom: 10px;
		    padding: 5px 0;
		}
		.fitem {
		    margin-bottom: 5px;
		}
		.fitem label {
		    display: inline-block;
		    width: 120px;
		}
	</style>
</head>
<body>
	<div style="padding:10px">  
		<div id="toolbar">  
	      	  <a href="javascript:void(0)" class="btn btn-small btn-primary" onclick="newUser(this)">New User</a>  
	          <a href="javascript:void(0)" class="btn btn-small btn-primary" onclick="editUser(this)">Edit User</a>  
	          <a href="javascript:void(0)" class="btn btn-small btn-primary" onclick="removeUser(this)">Delete User</a>  
	    </div>  
	    
	    <div class="blank"></div>
	    <div id="responseResult"></div>
	    
		<table class="table table-bordered table-hover">
		    <thead>
                <tr>
                    <th class="popover-title">No.</th>
                    <th class="popover-title">UserName</th>
                    <th class="popover-title">Password</th>
                    <th class="popover-title">Status</th>
                    <th class="popover-title">Create Date</th>
               </tr>
             </thead>
             <s:iterator value="pager.result" var="user" status="status">
             	<tr>
             		<td><s:property value="#status.index + 1"/></td>
             		<td><s:property value="#user.username"/></td>
             		<td><s:property value="#user.password"/></td>
             		<td>
             			<s:if test="#user.status == 1">
             				Normal
             			</s:if>
             			<s:elseif test="#user.status == 2">
             				Not Activated
             			</s:elseif>
             			<s:elseif test="#user.status == 3">
             				Locked
             			</s:elseif>
             			<s:else>
             				Cancelled
             			</s:else>
             		</td>
             		<td><s:property value="#user.createDateStr"/></td>
             	</tr>
             </s:iterator>
		</table>
     </div>
     
     <div id="dlg" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px"  
            closed="true" buttons="#dlg-buttons" title="This is the tooltip message."> 
        <div class="ftitle">User Information</div>       
        <form id="fm" method="post">  
        	<table>
        		<tr>
        			<td width="45%">
        				<h6>UserName:</h6>
        			</td>
        			<td width="55%">
        				<input type="text" name="userVo.username" class="easyui-validatebox" data-options="required:true">  
        			</td>
        		</tr>
        		<tr>
        			<td>
        				<h6>Password:</h6>
        			</td>
        			<td>
        				<input type="password" name="userVo.password" class="easyui-validatebox" data-options="required:true">  
        			</td>
        		</tr>
        		<tr>
        			<td>
        				<h6>Confirm Password:</h6>
        			</td>
        			<td>
        				<input type="password" name="userVo.confirmPassword" class="easyui-validatebox" data-options="required:true">  
        			</td>
        		</tr>
        		<tr>
        			<td>
        				<h6>User Status:</h6>
        			</td>
        			<td>
        				 <select id="userVo_status" name="userVo.status">
		                	<option value="1" selected="selected">Normal</option>
		                	<option value="2">Not Activated</option>
		                	<option value="3">Locked</option>
		                	<option value="4">Cancelled</option>
		                </select> 
        			</td>
        		</tr>
        	</table>
        </form>  
    </div>  
    <div id="dlg-buttons">  
        <a href="javascript:void(0)" class="btn btn-success" onclick="saveUser()">Save</a>  
        <a href="javascript:void(0)" class="btn btn-danger" onclick="clearForm()">Cancel</a>  
    </div>
</body>  
  <script type="text/javascript">
  	$(function(){
  		new chase.ui.select('userVo_status',{size:'small'});
  		
  		var responseCode = "${responseCode}";
		if(responseCode != ""){
			switch(responseCode){
				case "USER_SUCCESS_001" :
					setResponseResult("#responseResult","alert  alert-success","用户注册成功!");
				break;
				case "USER_SUCCESS_002" :
					setResponseResult("#responseResult","alert  alert-success","用户修改成功!");
				break;
				case "USER_SUCCESS_003" :
					setResponseResult("#responseResult","alert  alert-success","用户删除成功!");
				break;
				case "USER_FAIL_004" :
					setResponseResult("#responseResult","alert alert-error","用户注册失败!","用户名已经存在");
				break;
				case "USER_FAIL_005" :
					setResponseResult("#responseResult","alert alert-error","用户修改失败!","用户名已经存在");
				break;
				case "USER_FAIL_006" :
					setResponseResult("#responseResult","alert alert-error","用户删除失败!","该用户不存在");
				break;
				case "USER_FAIL_007" :
					setResponseResult("#responseResult","alert alert-error","用户注册失败!","两次输入的密码不一致");
				break;
				default:
					setResponseResult("#responseResult","alert alert-error","用户注册失败!","未知错误");
				break;
			}
		}
  	});
  	
  	function setResponseResult(selector,addClass,title,message) {
  		if(!message){
  			message = "";
  		}
  		
  		$("#responseResult").addClass(addClass)
  		.html("<button type='button' class='close' data-dismiss='alert'>×</button><strong>"+ title +"</strong>" + message);
  	}
  	
  	function newUser(href) {
  		$('#dlg').dialog('open').dialog('setTitle',$(href).text());  
  	}
  	
 	function editUser(href){  
		var row = $('#dg').datagrid('getSelected');  
        if (row){  
            $('#dlg').dialog('open').dialog('setTitle',$(href).text());  
            //$('#fm').form('load',userDataFilter(row));
        }  
    }  
    
    function clearForm() {
	    $('#fm').form('clear');
	    $('#dlg').dialog('close');
	}
    
  	function saveUser() {
  		$('#fm').attr('action','${path}/user/user!add.action');
  		$('#fm').submit();
  	}
  	
  	function removeUser() {
  		var row = $('#dg').datagrid('getSelected');  
        if (row){  
            $.messager.confirm('Confirm','Are you sure you want to delete this user?',function(r){  
                if (r){  
                    $.post('${path}/user/user!delete.action',{"userVo.id":row.id},function(result){  
                        if (result.success){  
                            $('#dg').datagrid('reload');    // reload the user data  
                        } else {  
                            $.messager.show({   // show error message  
                                title: 'Error',  
                                msg: result.errorMsg  
                            });  
                        }  
                    },'json');  
                }  
            });  
        }  
  	}
  	
  </script>
</html>
