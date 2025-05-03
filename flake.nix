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
          jdt-language-server
          typescript-language-server
          mockoon
          postman
        ];

        languages.java = {
          enable = true;
          jdk.package = pkgs.jdk17;
          maven.enable = true;
        };

        scripts = {
          db-conn.exec = "${pkgs.mysql84}/bin/mysql -u root";
        };
        env.DB_NAME = "estatedb";
        env.DB_USERNAME = "estate";
        env.DB_PASSWORD = "estate";
        env.JWT_SECRET = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

        services.mysql = {
          enable = true;
          package = pkgs.mysql84;
          initialDatabases = [
            { name = "estatedb"; schema = ./ressources/sql/script.sql;  }
          ];
          ensureUsers = [
            {
              name = "estate";
              password = "estate";
              ensurePermissions = {
                "estatedb.*" = "ALL PRIVILEGES";
              };
            }
          ];
        };
     };
    };
  };
}
