var app = angular.module("groupDiv", [
	'groupDiv.choicesController',
	'groupDiv.userController',
	'groupDiv.adminController',
	'groupDiv.createUeUser',
	'angular-google-gapi',
	'pascalprecht.translate',
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

app.config(function($translateProvider) {
	$translateProvider.useSanitizeValueStrategy(null);
	$translateProvider.preferredLanguage('fr');
    $translateProvider.translations('fr', translateFr);	
});
