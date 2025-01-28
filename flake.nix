{
  inputs = {
    env-nix-pkgs-proxy.url = "github:Aleod-m/env-nix-pkgs-proxy";
  };

  outputs = {self, env-nix-pkgs-proxy}:
  let
    proxyLib = env-nix-pkgs-proxy.lib;
  in proxyLib.mkFlake {
    perSystem = {pkgs, system}: {
      devShells.default = pkgs.mkShell {
        buildInputs = with pkgs; [
          nodejs_22
          typescript-language-server
          mockoon
          postman
          mysql84
          jdk17
          typescript-language-server
          maven
        ];
      };
    };
  };
}
