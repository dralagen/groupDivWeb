(function(){
	var app = angular.module('createUeUser', []);


	app.controller('StoreController', function($scope) {
			
		$scope.session = {};
		$scope.session.ues = [];
		
		
		$scope.deleteUe = function (ue) {
			var index = $scope.session.ues.indexOf(ue);
			$scope.session.ues.splice(index, 1);
		};	
		
		$scope.addUe = function () {	
			var ue = {
				title: '',
				author: {
				 name: '',
				}
			}	
			$scope.session.ues.push(ue);
		};
		
		$scope.createSession = function () {
			//TODO
		};
	
	});


		

})();
