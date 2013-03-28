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
	<div style="padding:5px">  
		 <table id="dg" class="easyui-datagrid" style="height:450px" url="${path}/user/user!userList.action"  
	            toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true">  
	          <thead>  
	              <tr>
	              	  <th data-options="field:'id',hidden:'true'">ID</th>    
	                  <th data-options="field:'username'" width="200">UserName</th>  
	                  <th data-options="field:'password'" width="100">Password</th>  
	                  <th data-options="field:'status'" width="100" sortable="true">Status</th>  
	                  <th data-options="field:'createDate'" width="100" dateFormat="yyyy-MM-dd" sortable="true">Create Date</th>
	              </tr>  
	          </thead>  
	      </table>
	      <div id="toolbar">  
	      	  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>  
	          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>  
	          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">Delete User</a>  
	    </div>  
     </div>
     
     <div id="dlg" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px"  
            closed="true" buttons="#dlg-buttons" title="This is the tooltip message."> 
        <div class="ftitle">User Information</div>       
        <form id="fm" method="post" novalidate>  
            <div class="fitem">  
                <label>UserName:</label>  
                <input type="text" name="userVo.username" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Password:</label>  
                <input type="password" name="userVo.password" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Confirm Password:</label>  
                <input type="password" name="userVo.confirmPassword" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>User Status:</label>  
                <select class="easyui-combobox" name="userVo.status" panelHeight='auto'>
                	<option value="1" selected="selected">Normal</option>
                	<option value="2">Not Activated</option>
                	<option value="3">Locked</option>
                	<option value="4">Cancelled</option>
                </select> 
            </div>  
        </form>  
    </div>  
    <div id="dlg-buttons">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveUser()">Save</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>  
    </div>
</body>  
  <script type="text/javascript">
  	
  	function newUser() {
  		$('#dlg').dialog('open').dialog('setTitle','New User');  
  		$('#fm').form('clear');  
  	}
  	
 	function editUser(){  
		var row = $('#dg').datagrid('getSelected');  
        if (row){  
            $('#dlg').dialog('open').dialog('setTitle','Edit User');  
            $('#fm').form('load',userDataFilter(row));
        }  
    }  
    
  	function saveUser() {
  		$('#fm').form('submit',{  
          url: "${path}/user/user!add.action",  
          onSubmit: function(){  
              return $(this).form('validate');  
          },  
          success: function(result){  
              var result = eval('('+result+')');  
              if (result.errorMsg){  
                  $.messager.show({  
                      title: 'Error',  
                      msg: result.errorMsg  
                  });  
              } else {  
                  $('#dlg').dialog('close');      // close the dialog  
                  $('#dg').datagrid('reload');    // reload the user data  
              }
          }  
      });  
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
  	
  	function userDataFilter(row) {
  		if (row){  
  			return {
  				id:row.id,
  				password:row.password,
  				createDate:row.createDate.year+"-"+row.createDate.month+"-"+row.createDate.date,
  				modifyDate:row.modifyDate.year+"-"+row.modifyDate.month+"-"+row.modifyDate.date,
  				username:row.username,
  				status:convterUserStatus(row.status)
  			};
  		}
  	}
  	
  	function convterUserStatus(status){
  		switch(status){
  			case 1: return 'Normal';
  			case 2: return 'Not Activated';
  			case 3: return 'Locked';
  			case 4: return 'Cancelled';
  			default: return '';
  		}
  	}
  </script>
</html>
