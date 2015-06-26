(function(){
	var app = angular.module('groupDiv.createUeUser', []);


	app.controller('createUeUser', ['$scope', 'GApi', function($scope, GApi){

		$scope.session = {};
		$scope.session.ue = [];
		$scope.alerts = [];

		$scope.deleteUe = function (ue) {
			var index = $scope.session.ues.indexOf(ue);
			$scope.session.ue.splice(index, 1);
		};

		$scope.addUe = function () {
			var ue = {
				title: '',
				user: '',
			}
			$scope.session.ue.push(ue);
		};
		//it don't work, pattern to follow: {"name": "un nom","ue": [{"title": "nom","user": "u1"}],"withGroupDiv": true}
		$scope.createSession = function () {
			console.log($scope.session);
			GApi.execute('groupDivWeb', 'session.post', $scope.session).then(
				function(resp) {
					console.log("session creted");
					$scope.alerts.push({type: 'success', msg: "Congratulation, session created!"});
					$scope.session = {};
					$scope.session.ue = [];
				}, function(err) {
					console.log("error you can't create that session : " + err.error.message);
					$scope.alerts.push({type: 'warning', msg: "Warning, the session can't be created, check if you don't make some mistakes!"});
				}
			);
		};

		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};

	}]);
})();
