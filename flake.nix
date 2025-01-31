{
  inputs = {
      nixpkgs.url = "github:cachix/devenv-nixpkgs/rolling";
      devenv.url = "github:cachix/devenv";
  };

  outputs = {self, flake-parts, nixpkgs, ...} @ inputs:
  flake-parts.lib.mkFlake {inherit inputs;} {
    imports = [ inputs.devenv.flakeModule ];
    systems  = nixpkgs.lib.systems.flakeExposed;
    perSystem = {config, self', inputs', pkgs, system, ...}: {
     devenv.shells.default = {
       packages = with pkgs; [
         nodejs_22
         typescript-language-server
         mockoon
         postman
         mysql84
         jdk17
         typescript-language-server
         maven
       ];
       scripts = {
         db-conn.exec = "${pkgs.mysql84}/bin/mysql -u root";
       };
       services.mysql = {
         enable = true;
         package = pkgs.mysql84;
         initialDatabases = [ { name = "estate-db"; schema = ./ressources/sql/script.sql; } ];
       };
     };
    };
  };
}
