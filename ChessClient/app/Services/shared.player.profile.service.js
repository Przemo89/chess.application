angular.module('app.component1').service('SharedPlayerProfileService', function() {
  'use strict';

   var data = {
     playerProfileAndStatistics: null
   };

   this.setPlayerProfileAndStatistics = function(playerProfileAndStatistics) {
     data.playerProfileAndStatistics = playerProfileAndStatistics;
   };

   this.getPlayerProfileAndStatistics = function() {
     return data.playerProfileAndStatistics;
   };

});
