angular.module('app.component1').factory('PlayerProfileService', ['$http', function($http) {
  'use strict';

  return {
    getPlayerProfileAndStatistics: function(idPlayer) {
      var url = 'http://localhost:8090/chess/player/profile/statistics/' + idPlayer;
      return $http.get(url, {cache: false});
    }
  }
}]);
