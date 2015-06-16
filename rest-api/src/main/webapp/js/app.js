var app = angular.module("groupDiv", [
	'groupDiv.choicesController',
	'groupDiv.userController',
	'groupDiv.adminController',
	'groupDiv.createUeUser',
	'angular-google-gapi',
	'ngRoute'
]);

app.run(['GApi', 'GAuth',
	function(GApi, GAuth) {
		var BASE;
		if(window.location.hostname == 'localhost') {
			BASE = '//localhost:8080/_ah/api';
		} else {
			BASE = 'https://groupdivxp.appspot.com/_ah/api';
		}
		GApi.load('groupDivWeb','v1',BASE);
		
	}
]);

app.config(['$routeProvider',
	function($routeProvider){
		$routeProvider.
			when('/adminCreate', {
				templateUrl: 'partial/adminCreate.html',
			}).when('/adminView', {
				templateUrl: 'partial/admin.html',
				controller: 'adminController',
			}).when('/user', {
				templateUrl: 'partial/userInterface.html',
			}).otherwise({
				templateUrl: 'partial/indexChoices.html',
			});
	}
]);
