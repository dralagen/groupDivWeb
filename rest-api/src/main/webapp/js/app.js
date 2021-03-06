var app = angular.module("groupDiv", [
	'groupDiv.choicesController',
	'groupDiv.userController',
	'groupDiv.adminController',
	'groupDiv.createUeUser',
	'groupDiv.groupDivController',
	'groupDiv.modifySession',
	'ngRoute',
	'angular-google-gapi',
	'pascalprecht.translate',
	'LocalStorageModule'
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
			}).when('/adminModify', {
				templateUrl: 'partial/adminModify.html',
			}).when('/adminView', {
				templateUrl: 'partial/admin.html',
				controller: 'adminController',
			}).when('/user/:userId/:sessionId', {
				templateUrl: 'partial/userInterface.html',
			}).otherwise({
				templateUrl: 'partial/indexChoices.html',
			});
	}
]);

//localstorage config
app.config(function (localStorageServiceProvider) {
	localStorageServiceProvider.setPrefix('groupDivWeb');
});

//language config
app.config(function($translateProvider) {
	$translateProvider.useSanitizeValueStrategy(null);
	$translateProvider.translations('fr', translateFr);
	$translateProvider.translations('en', translateEn);
	$translateProvider.preferredLanguage('fr');

});

app.controller("mainController", [ 'localStorageService','$translate' , function(localStorageService,$translate){
	if(localStorageService.get('language') == null)
		{$translate.use('fr'); }
	else
		{$translate.use(localStorageService.get('language'));}
}]);

app.factory('Users', function(){

	var usersType = {};
	usersType.users = {};
	usersType.currentUser = {id: "", name: ""};

	return usersType;
});




