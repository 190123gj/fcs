define(function(require, exports, module) {
	
	// 侧边栏 start
	(new(require('zyw/publicOPN'))()).addOPN([{
		name: '查看用户资料',
		url: '/fsd'
	}, {
		name: '查看项目详情',
		url: '/3ewr'
	}]).init().doRender();
	// 侧边栏 end
});