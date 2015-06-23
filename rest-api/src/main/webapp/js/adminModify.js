var app = angular.module('groupDiv.modifySession', []);

app.controller('modifySession', ['$scope','GApi' , function($scope,GApi){
	$scope.sessionChoosen = false;

	$scope.useGroupDiv = true;
	$scope.sessions = [];
	$scope.users = [];
	$scope.ues = [];
	$scope.list = [];
	
	
	GApi.execute('groupDivWeb', 'session.list').then(
		function(data){
			console.log("list sessions ok ");
			angular.forEach(data.items, function(item){
				temp = {name: item.name, id: item.id};
				$scope.sessions.push(temp);
			});
		},
		function(){
			console.log("error : we can't get the list of sessions");
		}
	);
	
	$scope.deleteUe = function (ue) {
		var index = $scope.list.indexOf(ue);
		$scope.list.splice(index, 1);
	};
	
	$scope.addUe = function () {	
		var ue = {
			user: '',
			ue: '',
		}	
		$scope.list.push(ue);
	};
	
	$scope.modifySession = function(){
		//TODO set session with the list
	}
	$scope.chooseSession = function(){
	if(!angular.isUndefined($scope.selectedSession)){
		$scope.sessionChoosen = true;
		
		GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.selectedSession}).then(
			function(data){
				console.log("session loaded");
				console.log(data);
				$scope.useGroupDiv = data.withGroupDiv;
				$scope.users = data.user;
				$scope.ues = data.ue;
				$scope.sessionName = data.name;
			}, function(){
				console.log("error : we can't load the session");
			}
		);
		$scope.sortUeUserList();
	}
	};
	
	//get a list of the users that are assign to the ues
	$scope.sortUeUserList = function(){
		angular.forEach($scope.ues, function(ue){
			angular.forEach($scope.users, function(user){
				if(ue.authorId == user.id)
				{ 
					temp = {user: user.name, ue: ue.title, ueId: ue.id};
					$scope.list.push(temp);
				}
			});
		});
	};
}]);