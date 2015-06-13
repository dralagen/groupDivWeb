(function(){
	var app = angular.module('groupDiv.createUeUser', []);


	app.controller('createUeUser', function($scope) {
			
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
