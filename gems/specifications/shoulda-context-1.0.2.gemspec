# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "shoulda-context"
  s.version = "1.0.2"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["thoughtbot, inc.", "Tammer Saleh", "Joe Ferris", "Ryan McGeary", "Dan Croak", "Matt Jankowski"]
  s.date = "2012-12-18"
  s.description = "Context framework extracted from Shoulda"
  s.email = "support@thoughtbot.com"
  s.executables = ["convert_to_should_syntax"]
  s.files = ["bin/convert_to_should_syntax"]
  s.homepage = "http://thoughtbot.com/community/"
  s.require_paths = ["lib"]
  s.rubygems_version = "1.8.15"
  s.summary = "Context framework extracted from Shoulda"

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_development_dependency(%q<mocha>, ["~> 0.9.10"])
      s.add_development_dependency(%q<rake>, [">= 0"])
      s.add_development_dependency(%q<test-unit>, ["~> 2.1.0"])
    else
      s.add_dependency(%q<mocha>, ["~> 0.9.10"])
      s.add_dependency(%q<rake>, [">= 0"])
      s.add_dependency(%q<test-unit>, ["~> 2.1.0"])
    end
  else
    s.add_dependency(%q<mocha>, ["~> 0.9.10"])
    s.add_dependency(%q<rake>, [">= 0"])
    s.add_dependency(%q<test-unit>, ["~> 2.1.0"])
  end
end
