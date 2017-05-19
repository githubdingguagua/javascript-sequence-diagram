//https://github.com/teampoltergeist/poltergeist/issues/292
//bind function is not defined in phantomjs 1.9.7 that we are using at the moment and
//if we dont use this hack then mvn build fails saying no bind function is defined in phantomjs
if(typeof Function.prototype.bind == 'undefined') {
    Function.prototype.bind = function(target) {
        var f = this;
        return function() {
          return f.apply(target, arguments);
        };
    };
}

