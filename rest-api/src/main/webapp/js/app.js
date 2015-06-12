var app = angular.module("groupDiv", [
	'groupDiv.choicesController',
	'groupDiv.userController',
	'groupDiv.adminController',
	'angular-google-gapi',
	'ngRoute'
]);

app.run(['GApi', 'GAuth',
	function(GApi, GAuth) {
		var BASE = '//localhost:8080/_ah/api';
		GApi.load('groupDivWeb','v1',BASE);
		
	}
]);

app.config(['$routeProvider',
	function($routeProvider){
		$routeProvider.
			when('/adminCreate', {
				templateUrl: '/adminCreate.html',
			}).when('/adminView', {
				templateUrl: '/adminView.html',
			}).otherwise({
				redirectTo: '/autre'
			});
	}
]);
