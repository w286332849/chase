/**
 * chase UI
 * 2013-1-29
 * 基于jquery1.8
 */
(function(window,$,undefined){
	var chase = window.chase || {},
		navigator = window.navigator,
		document = window.document,
		FN = chase.fn = {},
		UI = chase.ui = {};
		
	/* [animate动画扩展]
	 * 
	 * easeIn：加速度缓动；
	 * easeOut：减速度缓动；
	 * easeInOut：先加速度至50%，再减速度完成动画
	 */	
	$.extend($.easing,{
		//指数曲线缓动
		easeOutExpo: function (x, t, b, c, d) {
			return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
		}
	});
	FN.getRandom = function(){
		//得到一个随机数
		return String(Math.ceil(Math.random() * 100000) + String(new Date().getTime()));
	};
	FN.browser = function(){
		//检测浏览器
		var u = navigator.userAgent.toLowerCase();
		return {
			version:(u.match(/(?:firefox|opera|safari|chrome|msie)[\/: ]([\d.]+)/))[0],//浏览器版本号
		    safari:/version.+safari/.test(u),
		    chrome:/chrome/.test(u),
		    firefox:/firefox/.test(u),
		    ie:/msie/.test(u),
			ie6:/msie 6.0/.test(u),
			ie7:/msie 7.0/.test(u),
			ie8:/msie 8.0/.test(u),
			ie9:/msie 9.0/.test(u),
		    opera: /opera/.test(u) 
		}
	}();
	FN.type = function(o){
		//检测对象类型
		if(o == null) {
			return String(o);
		} //null或undefined
		var str = Object.prototype.toString.call(o); // 获取参数类型字符串
		return str.slice(8,str.length-1).toLowerCase();
	};
	FN.stopDefault = function(e){
		//取消事件的默认动作
		e = e || window.event;
		if (e.preventDefault) {
			e.preventDefault();
		} else {
			e.returnValue = false;
		}
	}
	FN.stopBubble = function(e){
		//阻止冒泡
		e = e || window.event;
		if (e.stopPropagation){
			e.stopPropagation();
		}else{
			e.cancelBubble = true;
		}
	}
	FN.cookie = function(name, value, options){
		/*
		 * 读取cookie值: chase.fn.cookie("key"); 
		 * 设置/新建cookie的值:	chase.fn.cookie("key", "value");
		 * 新建一个cookie 包括有效期(天数) 路径 域名等:chase.fn.cookie("key", "value", {expires: 7, path: '/', domain: 'zyj.com', secure: true});
		 * 删除一个cookie:chase.fn.cookie("key", null);	
		 */		
		if (typeof value != 'undefined') {
	        options = options || {};
	        if (value === null) {
	            value = '';
	            options.expires = -1;
	        }
	        var expires = '';
	        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
	            var date;
	            if (typeof options.expires == 'number') {
	                date = new Date();
	                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
	            } else {
	                date = options.expires;
	            }
	            expires = '; expires=' + date.toUTCString();
	        }
	        var path = options.path ? '; path=' + (options.path) : '';
	        var domain = options.domain ? '; domain=' + (options.domain) : '';
	        var secure = options.secure ? '; secure' : '';
	        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	    } else { 
	        var cookieValue = null;
	        if (document.cookie && document.cookie != '') {
	            var cookies = document.cookie.split(';');
	            for (var i = 0; i < cookies.length; i++) {
	                var cookie = jQuery.trim(cookies[i]);
	                if (cookie.substring(0, name.length + 1) == (name + '=')) {
	                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
	                    break;
	                }
	            }
	        }
	        return cookieValue;
	    }
	}	
	FN.tmpl = function(){
		/*
		 * js模版引擎
		 * http://ejohn.org/blog/javascript-micro-templating/
		 */
		var c = {};
		return function(s,d){
			var fn = !/\W/.test(s) ? c[s]=c[s]||FN.tmpl(document.getElementById(s).innerHTML):
			new Function("o",
			"var p=[];"+"with(o){p.push('"+
			s
			//.replace(/\\/g,"\\\\")//解决转义符bug
			.replace(/[\r\t\n]/g," ")			
			.split("<%").join("\t")
			.replace(/((^|%>)[^\t]*)'/g,"$1\r")
			.replace(/\t=(.*?)%>/g, "',$1,'")
			.split("\t").join("');")
			.split("%>").join("p.push('")
			.split("\r").join("\\'")
			+ "');}return p.join('');");
			return d?fn(d):fn;
		};
	}();
	
	//***********ui组件***********//
	UI.setPos = function(obj,pos,isFloat){
		/*
		 * 设置浮动元素显示位置
		 * @obj:设置对象
		 * @pos:{top:top,left:left},默认{50,50}屏幕居中,top和left值范围0-100
		 * @isFloat:是否浮动 0不浮动，1动画 2固定
		 */
		if(!obj){return;}
		pos = pos || {};
		var W = obj.outerWidth(),
			H = obj.outerHeight(),
			win = $(window),
			T,L,
			top = !pos.top&&pos.top!=0?50:pos.top,
			left = !pos.left&&pos.left!=0?50:pos.left,
			F = isFloat==0?0:isFloat,
			win_w,win_h,sTop,sLeft,s,
			ie6 = FN.browser.ie6;
		function getPos(){
			win_w = win.width();
			win_h = win.height();
			sTop = F==2&&!ie6?0:win.scrollTop();
			sLeft = F==2&&!ie6?0:win.scrollLeft();
			T = (win_h - H)*top/100 + sTop;
			L = (win_w - W)*left/100 + sLeft;
		}
		getPos();
		obj.css({
			'top' : T,
			'left' : L,
			'position' : F==2&&!ie6?"fixed":"absolute"
		})
		
		function moveTo(resize){
			getPos();
			s = {
				top : T,
				left : L
			};
			if(F==1){
				$(obj).stop().animate(s,180);
			}else if(F==2){
				obj.css(s);
			}
		}
		F&&win.bind('scroll resize',moveTo);
	}
	
	UI.layer = function(){
		/*
		 * 遮罩层
		 */
		var w = $(window),
			layer = $("#nj_layer"),
			arr = {show:show,hide:hide,isShow:false};
		function init(){
			layer = $("body").append('<div id="nj_layer"></div>').find("#nj_layer");
	        layer.css({
				"opacity":"0"
			})
			S = function(){
				layer.css({
					width:w.width(),
					height:w.height()
				})
			}
			S();
			w.scroll(S).resize(S);
			chase.ui.setPos(layer,{top:0,left:0},2);
		}
		function show(){
			!layer.length&&init();
			arr.self = layer;
			arr.isShow = true;
			if(layer.is(":visible")){
				return;
			}
			layer.css({
				"display": "block"
			}).animate({"opacity":"0.7"},150);
		}
		function hide(){
			arr.isShow = false;
			layer.animate({"opacity":"0"},150,function(){
				layer.css("display","none");
			});
		}
		
		return arr;
	}();
	
	UI.win = function(opt){
		/*
		 * 弹窗
		 */
	    this._op = opt = opt||{};
	    this.w = opt.width||"400";//宽
	    this.self = null;//弹窗对象本身
	    this.close = null;//关闭按钮
	    this.tit = null;//标题区
	    this.con = null;//放置内容的容器对象    
		this.opt = null;//操作区		
		this.stillLayer = opt.stillLayer||false;//隐藏后是否继续显示遮罩层
		this.layer = opt.layer==false?false:true;
		this.pos = opt.pos||{left:50,top:50};
		this.Float = opt.Float||opt.Float===0?opt.Float:2;
		this.bindEsc = opt.bindEsc==false?false:true;
		this.onShow = opt.onShow;
		this.onHide = opt.onHide;
		this.init();
	}
	UI.win.prototype = {
		init : function(){	
			var me = this,
				id = 'nj_win_' + FN.getRandom(),
				win = [
					'<div id="'+id+'" class="nj_win"><div class="win_wrap">',
						'<span class="win_close"></span><div class="win_tit"></div>',
						'<div class="win_con"></div>',
						'<div class="win_opt"></div>',
					'</div></div>'
				];
			
			UI.win.item[id] = this;
			this.key = id;
			$("body").append(win.join(''));//插入到body中
			this.self = $("#"+id).css({'width':me.w,'opacity':'0'});
			this.close = this.self.find(".win_close");
			this.tit = this.self.find(".win_tit");
			this.con = this.self.find(".win_con");
			this.opt = this.self.find(".win_opt");
			new UI.ico('close',this.close);
			this.bind();
		},
		bind : function(){
			var T = this;
			this.close.click(function(){T.hide();});//绑定关闭按钮事件
			if(this.bindEsc){
				$(window).bind("keydown",function(e){//按下esc键隐藏弹窗
					T.self.is(":visible")&&e.keyCode==27&&T.hide();
				})
			}
			/*
			this.self.ajaxStart(function(){
				!T.ajaxLoading&&createLoad();
				T.ajaxLoading.show();
			}).ajaxComplete(function(){
				T.ajaxLoading&&T.ajaxLoading.hide();
			});
			function createLoad(){
				T.ajaxLoading = $(document.createElement('em')).attr({'class':'nj_ico n_i_loading ajax_loading'});
				T.self.append(T.ajaxLoading);
				new UI.ico('loading',T.ajaxLoading);
			}	
			 */
		},		
	    setCon : function(tit,con,btns){
	    	/*
				设置标题、内容
		        @tit,con:会替换以前的
		        @btns:设置操作区按钮，为一个数组，数组项格式同this.getButton,如不设置或设置null则隐藏操作区
		    */
		   	tit&&this.tit.html(tit);
		   	con&&this.con.html(con);
			if(btns){
				this.opt.empty();//重设操作区
				this.button = [];
				for(var i=0;i<btns.length;i++){
					this.addBtn.apply(this,btns[i]);
				}
			}else if(this.opt.html().replace(/\s/g,"")==""){//不传入且未设置过操作区则隐藏操作区
				this.opt.css("display","none");
				this.button = [];
			}
	    },
		addBtn : function(text,callback,color){
			/*
				增加一个操作区按钮
				@text:按钮文字
				@color:按钮样式，即class类名
				@callBack:按钮click绑定的函数,"close"则为关闭
			*/			
			this.opt.is(":hidden")&&this.opt.show();
			var me = this,
				btn = $(document.createElement("a")),
				color = color?color:"";			
			if(typeof callback=='string'&&callback!='close'){//无回调时，第二个参数作为按钮颜色
				color = callback;
				callback = null;
			}	
			btn.attr({"class":"nj_btn n_b_"+color});
			btn.html('<i>'+text+'</i>');
			this.opt.append(btn);
			this.button.push(btn);
			if(callback) {
				if (callback == "close") {
					callback = function(){
						me.hide();
					}
				}
				btn.bind("click",function(){
					callback();
					return false;
				});
			}
		},
	    show : function(callBack){ 
	    	/*
				显示弹窗
		        @callBack:可选参数，回调函数
		    */
			if(this.self.is(":visible")){return;}
			UI.setPos(this.self,this.pos,this.Float);	//重新计算高度并设置居中 
			this.layer&&UI.layer.show();
			this.self.css({
				"display":"block",
				"margin-top":"-15px"
			})
			this.self.stop().animate({
				"opacity":"1",
				"margin-top":"0"
			},480);
			window.setTimeout(function(){
				callBack&&callBack();
			},200);
			this.onShow&&this.onShow();
	    },
		/*
			隐藏弹窗
	        @callBack:可选参数，回调函数
	    */
	    hide : function(callBack){
			if(this.self.is(":hidden")){return;}
			var T = this;
			/*
			 * onbeforehide:关闭之前确认，传入一个数组[fn,true/false]
			 * fn返回的布尔值;第二个参数true表示是否只有当fn为true时才关闭窗口,false均关闭
			 */
			if(this.onbeforehide && !this.onbeforehide[0]()){			
				if(this.onbeforehide[1]) {return false;}
			}//关闭之前确认
			this.self.animate({
				"margin-top":"-15px",
				"opacity":"0"
			},150,function(){
				T.self.css({"display":"none"});
	       	 	if(callBack){callBack();}
			});
	        if (!this.stillLayer) {
				UI.layer.hide();
			}
			this.onHide&&this.onHide();	
	    }
	}
	UI.win.item = {};//保存所有弹框实例对象
	UI.win.clear = function(key){
		//清空弹框对象
		if(key){
			var win = UI.win.item[key];
			win&&clear(win);
		}else{
			for(var i in UI.win.item){
				clear(UI.win.item[i]);
			}
			UI.win.item = {};
			UI.msg.win = null;
		}
		function clear(win){
			win.self.remove();
			win = null;
		}
	}
	
	UI.msg = function(){
		/*
		 * 消息提示框
		 */
		var win,ICO={},delay=250;
		return {
			show : function(type,tip,opt){
				opt = opt || {};
				var T = this,
					btn = opt.btn,
					time = opt.time || 1600,
					ico,
					w = opt.width||'auto',
					C = type=='confirm';
				if(this.win&&(win.self.is(':visible')||win.self.is(':animated'))){
					//多消息框同时显示后者延迟执行
					delay += Math.random()*200;
					window.setTimeout(function(){
						win.hide();
						T.show(type,tip,opt);
					},delay);
					return;
				}
				tip = tip || '';
				if(!this.win){
					win = new UI.win({
						width:w,
						bindEsc:false
					});
					win.self.addClass('msg_tip_win');
					this.win = win;
				}
				win.self.width(w);
				win.layer = C?true:false;
				win.stillLayer = C?false:true;
				if(type=='loading'){
					tip = tip||'正在处理请求,请稍候……';
				}else if(C){
					btn = btn || [
						['确定',function(){
							win.hide(function(){
								try{opt.ok();}catch(e){};
							});
						}],
						['取消','close']
					];
				}
				ico = C?'warn':type;
				if(!btn){
					win.opt.empty();//重设操作区
				}
				win.setCon(null,'<div class="con"><i class="tip_ico"></i><span class="c">'+tip+'</span></div>',btn);
				if(!ICO[ico]){
					ICO[ico] = new UI.ico(ico,win.con.find('.tip_ico'));
				}else{
					win.con.find('.tip_ico').append(ICO[ico].ico);
				}				
				win.show();
				this.timeout = window.clearTimeout(T.timeout);
				if(type!='confirm'&&type!='loading'){
					this.timeout = window.setTimeout(function(){
						win.hide();
					},time)
					if(opt.reload){
						window.setTimeout(function(){
							if(opt.reload===true){
								window.location.reload();
							}else if(typeof opt.reload=='string'){
								window.location.href = opt.reload;
							}
						},700)
					}
				}
			},
			hide : function(){				
				win&&win.hide();
			}
		}
	}();
	
	UI.select = function(box,opt){
		/*
		 * 通用下拉列表,延迟200毫秒触发
		 * @opt:可选项{show:只显示几项,只接受整数，固定高度,onSelect:根据value值来匹配切换事件}
		 * onChange{value1:callBack,value2:callBack}，当选择某项的时候触发一个回调
		 * 默认选中项根据隐藏域的value值来匹配
		 */		
		opt = opt || {};
		this.box = $("#"+box);
		if(!this.box.length){return;}
		this.show = opt.show;
		this.onChange = opt.onChange;
		this.maxH = "auto";//展开时的最大高度
		this.onSelect = opt.onSelect;//切换回调
		this.defaultEvent = opt.defaultEvent==true?true:false;//首次是否执行回调
		this.size = opt.size;//{size:'big/middle/small'}
		this.autoWidth = opt.autoWidth==false?false:true;
		this.value = null;
		this.now = null;
		this.setCon();
	};
	UI.select.prototype = {
		setCon : function(){
			var HTML = this.box.html().replace(/OPTION|option/g,'li'),
				list = this.box.find('option'),
				html = '',m,
				link,
				style = this.box.attr('style'),
				v = this.box.attr("val")||this.box.val()||0,
				i,j;
			this.len = list.length;
			html = '<span class="nj_select"><span class="wrap"><i></i><div class="nj_bg"></div><div class="nj_arrow"></div><ul></ul>';
			html += '<input type="hidden" name="'+this.box.attr('name')+'" value="'+v+'" class="hide" />';
			html += '</span></span>';
			this.box.after(html);
			this.box.hide();
			this.box = this.box.next();
			this.box.prev().remove();
			this.box.attr('style',style);
			
			this.menu = this.box.find("ul");
			this.menu.html(HTML);
			this.m_on = this.box.find("i");
			this.hide = this.box.find(".hide");
			if(this.size=='middle'){
				this.box.addClass('nj_select_m');
			}
			if(this.size=='small'){
				this.box.addClass('nj_select_s');
			}
			this.init();
		},
		init : function(I){
			this.m = this.menu.find('li');
			this.len = this.m.length;
			this.h = this.m.last().outerHeight();
			var _m,at,
				d = this.hide.val(),
				pd = parseInt(this.m_on.css("padding-left"))+parseInt(this.m_on.css("padding-right")),
				w1,w2 = 0,w3;
			this.m_on.removeAttr("style href");
			this.menu.removeAttr("style").css({
				"display":"block"
			})
			for(var i=0;i<this.len;i++){
				_m = this.m.eq(i);
				w1 = _m.width();
				if(w1>w2){w2 = w1;}
				if(_m.attr('href')){
					_m.wrapInner(document.createElement("a"));
					_m.find('a').attr('href',_m.attr('href'));
					_m.addClass("link");
				}
			}
			
			if(this.show && /^\d+$/.test(this.show)){//显示固定个数
				if(this.show<this.len){
					this.menu.css({"overflow-y":"scroll"});
					this.maxH = this.h * this.show - 1;
				}else{
					this.menu.css({"overflow-y":"hidden"});
					this.maxH = this.h * this.len - 1;
				}
				w3 = this.menu.width();
				if(w3 > (w2 + pd)){w2 = w3 - pd;}
			}
			
			this.menu.css({
				"height":this.maxH,
				"display":"none"
			})
			if(this.autoWidth){
				this.menu.css('width',w2+pd);
				this.m_on.width(w2);
			}else{
				this.m_on.width(this.box.width()-pd-2);
				this.menu.width(this.box.width()-2);
			}
			this.m.removeClass('last').last().addClass('last');
			if(!I){
				this.bindEvent();
			}
			//设置默认选项
			if(d.replace(/\s/g,"")!=""){
				this.select(d,this.defaultEvent);
			}else{
				this.select(this.m.eq(0).attr("value"),this.defaultEvent);
			}
		},
		bindEvent : function(){
			var T = this,A,top,style;
			this.box.hover(function(){
				window.clearTimeout(A);
				if(T.menu.is(':visible')){return;}
				A = window.setTimeout(function(){
					style = T.menu.attr('style').replace('top','top1').replace('bottom','top1');
					T.menu.attr('style',style).css({"display":"none"});
					T.box.addClass('nj_select_hover');
					top = T.m_on.outerHeight()-1;
					if((T.box.offset().top-$(window).scrollTop()+T.box.height()+T.menu.height())>$(window).height()){
						T.menu.css('bottom',top);
					}else{
						T.menu.css('top',top);
					}
					T.menu.slideDown(200,'easeOutExpo');
				},150)
			},function(){
				window.clearTimeout(A);
				A = window.setTimeout(function(){
					T.menu.slideUp(150,'easeOutExpo');
					T.box.removeClass('nj_select_hover');
				},200)
			});
			
			this.menu.click(function(e){
				var m = e.target,
					text = $(m).text(),
					v = m.getAttribute('value');
				if(m.tagName.toLowerCase()=='li'){
					T.select(v);
				}
			})
			/*
			this.box.ajaxStart(function(){
				!T.ajaxLoading&&createLoad();
				T.ajaxLoading.show();
			}).ajaxComplete(function(){
				T.ajaxLoading&&T.ajaxLoading.hide();
			});
			function createLoad(){
				T.ajaxLoading = $(document.createElement('em')).attr({'class':'nj_ico n_i_loading ajax_loading'});
				T.box.append(T.ajaxLoading);
				new UI.ico('loading',T.ajaxLoading);
			}
			*/
		},
		select : function(v,I){
			var M,text,m;
			for(var i=0;i<this.len;i++){
				m = this.m.eq(i);
				if(m[0].getAttribute('value')==v){
					M = m;
					text = m.text();
					break;
				}
			}
			if(!M){return;}
			this.m_on.text(text);
			M.addClass("select").siblings().removeClass("select");
			this.hide.val(v);
			this.value = v;
			this.now = M;
			this.menu.css({"display":"none"});
			
			if(I===false){return;}
			//为其添加事件
			this.onChange&&this.onChange[v]&&this.onChange[v]();
			this.onSelect&&this.onSelect.call(this,v,M);
		}
	}
	UI.select.batch = function(box,opt){
		/*
		 * 批量生成select组件(全部select只能统一设置,特殊情况时不能用此方法)
		 * @box:传入父元素对象即可
		 */
		if(!box||!box.length){return;}
		var s = box.find("select"),
			m,
			id,
			arr = [],
			n = s.length,
			d;
		if(!n){return;}	
		for(var i=0;i<n;i++){
			m = s.eq(i);
			id = m.attr("id");
			if(!id||id.replace(/\s/g,"")==""){
				id = "s_"+FN.getRandom();
				m.attr("id",id);
			}
			d = new this(id,opt);
			arr.push(d);
		}
		return arr;
	}
	
	UI.Switch = function(id,opt){
		/*
		 * switch原型超类|幻灯片、选项卡等
		 * @id:容器id
		 * 子类不能通过该构造函数传递参数，所以使用init方法来传递
		 */
		if(!id){return;}
		this.init(id,opt);
	}
	UI.Switch.prototype = {
		init : function(id,opt){
			this.box = $("#"+id);
			if(!this.box.length){return;}
			this.M = this.box.find(".nj_s_menu:first");
			this.menu = this.M.find(".nj_s_m");
			this.C = this.box.find(".nj_s_con:first");
			this.con = this.C.find(".nj_s_c");
			this.len = this.con.length;
			if(!this.len){return;}
			this.opt = opt || {};
			this.mode = this.opt.mode=='click'?'click':'mouseover';
			this.onChange = this.opt.onChange;
			this.index = this.opt.firstIndex || 0;
			this.bind();
		},
		bind : function(){
			var T = this,
				A,m,
				delay = T.mode=='mouseover'?100:0;//延迟触发
			this.menu.bind(this.mode,function(){
				m = $(this);
				if(m.hasClass('current')){return;}
				A = window.setTimeout(function(){
					T.change(m.index());
				},delay)
			}).mouseout(function(){
				A&&window.clearTimeout(A);
			})
			this.change(this.index);
		},
		change : function(index){
			index = index>(this.len-1) ? 0 : index;
			index = index<0 ? (this.len-1) : index;
			if(this.opt.rule){
				this.opt.rule.call(this,index);
			}else if(this.rule){
				this.rule(index);
			}else{
				this.con.eq(index).show().siblings().hide();
				this.menu.eq(index).addClass("current").siblings().removeClass("current");
			}
			this.index = index;
			if(this.onChange){this.onChange.call(this,index);}
		}
	};
	
	UI.slide = function(id,opt){
		/*
		 * switch扩展: slide幻灯片
		 */	
		this.init(id,opt);
		if(!this.box.length){return;}
		this.getIndexNum = this.opt.getIndexNum==true?true:false;
		this.getIndexNum && this.getNum();
		this.play = null;
		this.time = this.opt.time || 6000;
		this.auto = this.opt.auto==false?false:true;
		this.stopOnHover = this.opt.stopOnHover==false?false:true;
		this.start(true);
	}
	UI.slide.prototype = new UI.Switch();
	UI.slide.prototype.constructor = UI.slide;
	UI.slide.prototype.getNum = function(){
		var list = '';
		for(var i=1;i<=this.len;i++){
			list += '<li class="nj_s_m">'+i+'</li>';
		}
		this.M.append(list);
		this.menu = this.M.find('.nj_s_m');
		this.bind();
	}
	UI.slide.prototype.rule = function(index){
		//切换规则
		this.con.stop().eq(index)
		.css({'z-index':'1','display':'block','opacity':'0','left':'10px'})
		.animate({'opacity':'1','left':'0'},700)
		.siblings().fadeOut(900,0).css({'z-index':this.len-1});
		this.menu.eq(index).addClass("current").siblings().removeClass("current");
		this.index = index;
	}
	UI.slide.prototype.start = function(startNow){
		//自动播放
		var T = this;
		if(this.auto&&this.len>1){
			if(this.stopOnHover){
				this.box.unbind().hover(function(){
					T.play = window.clearInterval(T.play);
				},function(){
					s();
				}).mouseout();
			}else{
				s();
			}
		}
		startNow&&T.change(T.index);
		function s(){
			window.clearInterval(T.play);
			T.play = window.setInterval(function(){
				T.change(++T.index);
			},T.time);
		}
	}
	
	UI.ico = function(type,obj,opt){
		/*
		 * canvas/vml绘制的图标
		 */		
		opt = opt||{};
		this.hasCanvas = !!document.createElement('canvas').getContext;
		this.ico = $('<i class="nj_ico n_i_'+type+'"></i>');
		obj&&obj.length&&obj.empty();
		this.obj = obj || $('body:first');
		this.ico.css('visibility','hidden');
		this.obj.append(this.ico);
		this.type = type;
		this.canvas = null;
		this.ctx = null;
		this.width = opt.width||this.ico.width();
		this.height = opt.height||this.ico.height();
		if(!this.width||!this.height){return;}
		this.color = opt.color||this.ico.css('color');
		this.bgcolor = opt.bgcolor||this.ico.css('background-color');
		this.ico.removeAttr('style');
		this.ico.css({'background':'none','width':this.width,'height':this.height});
		this.createSpace();
	}
	UI.ico.prototype = {		
		createSpace : function(){
			var d = document;
			if(this.hasCanvas){
				this.canvas = d.createElement('canvas');
				this.ctx = this.canvas.getContext('2d');
				this.canvas.width = this.width;
				this.canvas.height = this.height;
				this.ico.append(this.canvas);
			}else{
				if(!chase.ui.ico['iscreatevml']){//只创建 一次vml
					var s = d.createStyleSheet(),
						shapes = ['polyline','oval','arc','stroke','shape'];
					d.namespaces.add("v", "urn:schemas-microsoft-com:vml"); //创建vml命名空间
					for(var i=0;i<shapes.length;i++){
						s.addRule("v\\:"+shapes[i],"behavior:url(#default#VML);display:inline-block;");
					}
					chase.ui.ico['iscreatevml'] = true;
				}
				this.ico.css('position','relative');
			}
			this.draw();
		},
		drawLine : function(point,fill,border){
			var i,n = point.length;
			if(this.hasCanvas){
				this.ctx.beginPath();
				this.ctx.moveTo(point[0],point[1]);
				for(i=2;i<n;i+=2){
					this.ctx.lineTo(point[i],point[i+1]);
				}
				this.ctx.stroke();
				fill&&this.ctx.fill();
			}else{
				var path = '',v = '';
				for(i=0;i<n;i+=2){
					path += point[i]+','+point[i+1]+' ';
				}
				v += '<v:polyline strokeWeight="'+border+'" filled="false" class="polyline" strokecolor="'+this.color+'" points="'+path+'" ';
				if(fill){
					v += 'fillcolor="'+this.color+'"';
				}
				v += '/>';
				$(this.canvas).after(v);
			}
		},
		draw : function(){
			var startAngle,endAngle,border,point,
				p = Math.PI,
				width = this.width,
				height = this.height,
				color = this.color,
				bgcolor = this.bgcolor,
				ctx = this.ctx,
				canvas = this.canvas,
				type = this.type,
				d = document,
				T = this;
			if(type=='loading'){
				border = 3;
				if(this.hasCanvas){
					startAngle = p / 180;
					endAngle = 200*p / 180;
					ctx.strokeStyle = this.color;
					ctx.lineWidth = border;
					window.setInterval(function(){
						ctx.clearRect(0,0,width,height);
						startAngle += 0.1;
						endAngle += 0.1;
						ctx.beginPath();
						ctx.arc(width/2,height/2,width/2-border+1,startAngle,endAngle,false);
						ctx.stroke();
					},15);
				}else{
					startAngle = 0;
					border--;
					this.canvas = d.createElement('<v:arc class="oval" filled="false" style="left:1px;top:1px;width:'+(width-border*2+1)+'px;height:'+(height-border*2+1)+'px" startangle="0" endangle="200"></v:arc>');
					$(this.canvas).append('<v:stroke weight="'+border+'" color="'+color+'"/>');
					this.ico.append(this.canvas);
					window.setInterval(function(){
						startAngle +=6;
						startAngle = startAngle>360?startAngle-360:startAngle;
						T.canvas.rotation = startAngle;
					},15);
				}
			}else if(type=='ok'||type=='warn'||type=='error'||type=='close'){
				if(this.hasCanvas){
					ctx.beginPath();
					ctx.fillStyle = bgcolor;
					ctx.arc(width/2,height/2,width/2,p,3*p,false);
					ctx.fill();
					ctx.fillStyle = color;
					ctx.strokeStyle = color;
				}else{
					this.canvas = d.createElement('<v:oval class="oval" fillcolor="'+bgcolor+'" style="width:'+(width-1)+'px;height:'+(height-1)+'px;"></v:oval>');
					$(this.canvas).append('<v:stroke color="'+bgcolor+'"/>');
					this.ico.append(this.canvas);
				}
				
				if(type=='ok'){
					point = [0.26*width,0.43*height , 0.45*width,0.59*height , 0.71*width,0.32*height , 0.71*width,0.46*height , 0.45*width,0.73*height , 0.26*width,0.57*height , 0.26*width,0.43*height];
					this.drawLine(point,true);
				}else if(type=='warn'){
					if(this.hasCanvas){
						ctx.beginPath();
						ctx.arc(width*0.5,height*0.73,width*0.07,p,3*p,false);
						ctx.stroke();
						ctx.fill();
					}else{
						width = width*0.96;
						height = height*0.96;
						this.ico.append('<v:oval class="oval" fillcolor="#fff" style="width:'+height*0.16+'px;height:'+height*0.14+'px;left:'+(height*0.4)+'px;top:'+(height*0.68)+'px"><v:stroke color="#fff"/></v:oval>');
					}
					point = [0.45*width,0.22*height , 0.55*width,0.22*height , 0.55*width,0.54*height , 0.45*width,0.54*height , 0.45*width,0.22*height];
					this.drawLine(point,true);
				}else if(type=='error'||type=='close'){
					if(!this.hasCanvas){
						width = width*0.95;
						height = height*0.95;
					}
					point = [0.32*width,0.28*height , 0.5*width,0.46*height , 0.68*width,0.29*height , 0.73*width,0.34*height , 0.55*width,0.51*height , 0.73*width,0.66*height , 0.68*width,0.73*height , 0.5*width,0.56*height , 0.34*width,0.72*height , 0.29*width,0.66*height , 0.46*width,0.51*height , 0.28*width,0.32*height];
					this.drawLine(point,true);
					function bind(){
						if(T.hasCanvas){
							T.ico.hover(function(){
								ctx.clearRect(0,0,width,height);
								ctx.beginPath();
								ctx.fillStyle = color;
								ctx.strokeStyle = bgcolor;	
								ctx.arc(width/2,height/2,width/2,p,3*p,false);
								ctx.fill();
								ctx.stroke();
								ctx.fillStyle = bgcolor;
								T.drawLine(point,true);
							},function(){
								ctx.clearRect(0,0,width,height);
								ctx.beginPath();
								ctx.fillStyle = bgcolor;
								ctx.strokeStyle = bgcolor;
								ctx.arc(width/2,height/2,width/2,p,3*p,false);
								ctx.fill();
								ctx.stroke();
								ctx.fillStyle = color;
								ctx.strokeStyle = color;
								T.drawLine(point,true);
							})
						}else{
							T.ico.hover(function(){
								var a = $(this).find('.oval')[0],b = $(this).find('.polyline')[0];
								a.fillcolor = a.strokecolor = color;
								b.fillcolor = b.strokecolor = bgcolor;
							},function(){
								var a = $(this).find('.oval')[0],b = $(this).find('.polyline')[0];
								a.fillcolor = a.strokecolor = bgcolor;
								b.fillcolor = b.strokecolor = color;
							})
						}
					}
					type=='close'&&bind();
				}
			}else{
				//自定义绘图方法
				this['Draw'+type]&&this['Draw'+type]();
			}
		}
	}
	UI.ico.batch = function(obj,opt){
		/*
		 * 批量生成图标
		 */
		var i,m,len,j,ico = {},T=this;
		for(i in obj){
			m = obj[i];
			len = m.length;	
			if(len>1){
				for(j=0;j<len;j++){
					new T(i,m.eq(j),opt);
				}
			}else if(len==1){
				new T(i,m,opt);
			}else{
				continue;
			}
			ico[i] = m;	
		}
		return ico;
	}
	UI.ico.add = function(type,draw){
		/*
		 * 添加自定义绘图方法
		 */
		if(!UI.ico.prototype['Draw'+type]){
			UI.ico.prototype['Draw'+type] = draw;
		}
	}
	
	UI.menu = function(obj,opt){
		/*
		 * 下拉菜单，动态创建html
		 */
		if(!obj||!obj.length){return;}
		this.opt = opt = opt || {};
		this.obj = obj;
		this.menu = null;
		this.content = opt.content || '';//菜单内容
		this.align = opt.align || 'left';//对齐方式
		this.mode = opt.mode || 'mouseover';
		this.offset = opt.offset || [0,0];
		this.autoWidth = opt.autoWidth==false?false:true;
		this.pos = this.offset[2]&&this.offset[2].length?this.offset[2]:this.obj;
		this.now = this.obj.eq(0);
		this.disable = false;//是否禁用菜单
		this.init();
	}
	UI.menu.prototype = {
		init : function(){
			this.menu = $('<div class="nj_menu"><div class="wrap"></div></div>');
			$('body').append(this.menu);
			this.setCon();
			this.bind();
			if(this.opt.className){
				this.menu.addClass(this.opt.className);
			}
		},
		bind : function(){
			var T = this,
				s = this.mode=='mouseover',
				delay = s||this.mode=='focus'?120:0,
				top,left,
				A,B;
			this.obj.bind(this.mode,function(e){
				FN.stopBubble(e);
				return show($(this));
			});
			if(s){
				this.obj.bind('mouseout',function(){
					A = window.clearTimeout(A);
					hide();
				})
				this.menu.hover(function(){
					B = window.clearTimeout(B);
				},hide)
			}else if(this.mode=='focus'){
				this.obj.bind('blur',function(){
					A = window.clearTimeout(A);
					hide();
				})				
			}
			if(!s){
				this.menu.bind('click',function(e){
					B = window.clearTimeout(B);
					FN.stopBubble(e);
				})
				$(document).bind('click',hide);
			}
			function show(pos){
				if(T.disable){return false;}
				if(T.mode=='click'&&T.menu.is(':visible')){
					if(T.now.is(pos)){
						hide();
						return false;
					}
					hide(true);
				}else{
					B = window.clearTimeout(B);
				}
				T.opt.onShow&&T.opt.onShow.call(T,pos);
				T.now = pos;
				A = window.setTimeout(function(){
					pos.addClass('nj_menu_hover');//.find('.nj_arrow').addClass('n_a_top');
					T.setPos();
				},delay);
				if(!s){return false;}
			}
			
			function hide(S){
				B = window.setTimeout(function(){
					T.obj.removeClass('nj_menu_hover');//.find('.nj_arrow').removeClass('n_a_top');
					if(S!==true){
						T.menu.hide();
						T.opt.onHide&&T.opt.onHide.call(T);
					}
				},delay)
			}
			$(window).bind('scroll resize',function(){
				T.menu.is(':visible')&&T.setPos();
			});
			
			(function(){
				//为列表项添加自定义事件
				var bind = T.opt.onSelect,
					i,m;
				if(!bind){return;}
				for(i in bind){
					m = T.menu.find('['+i+']');
					if(m.length){
						(function(i){
							m.bind('click',function(){
								bind[i].call(T,$(this));
								hide();
								return false;
							})
						})(i);
					}
				}
			})();
		},
		setPos : function(){
			var T = this,
				ph = this.pos.outerHeight(),
				pt = this.pos.offset().top,
				pl = this.pos.offset().left,
				left = pl+this.offset[0],
				top = pt+ph+this.offset[1];
			if(this.align=='right'){
				left -= this.menu.outerWidth()-this.pos.outerWidth();
			}
			this.menu.removeAttr('style').css({
				'left' : left,
				'top' : top,
				'display':'block'
			})
			if(!this.autoWidth){
				this.menu.width(this.pos.outerWidth()-2);
			}
			(function(){
				var h = T.menu.outerHeight(),
					H = $(window).height();
				if(top+h>H){
					top = pt-h;
					if(top<0){
						T.menu.css({'top':0,'overflow':'auto','height':pt-2-T.offset[1]});
						top = 0;
					}else{
						T.menu.css({'top':top-T.offset[1]});
					}
				}
			})();
		},
		setCon : function(con){
			con = con || this.content;
			this.menu.children('.wrap').html(con);
		}
	};
	
	window.chase = chase;
})(window,jQuery);