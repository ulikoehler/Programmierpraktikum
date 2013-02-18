# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "test-unit"
  s.version = "1.2.3"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Kouhei Sutou", "Ryan Davis"]
  s.date = "2008-03-20"
  s.description = "Test::Unit (Classic) - Nathaniel Talbott's originial test-unit, externalized from the ruby project as a gem (for tool developers)."
  s.email = ["kou@cozmixng.org", "ryand-ruby@zenspider.com"]
  s.executables = ["testrb"]
  s.extra_rdoc_files = ["History.txt", "Manifest.txt", "README.txt"]
  s.files = ["bin/testrb", "History.txt", "Manifest.txt", "README.txt"]
  s.homepage = "http://rubyforge.org/projects/test-unit/"
  s.rdoc_options = ["--main", "README.txt"]
  s.require_paths = ["lib"]
  s.rubyforge_project = "test-unit"
  s.rubygems_version = "1.8.15"
  s.summary = "Test::Unit (Classic) - Nathaniel Talbott's originial test-unit, externalized from the ruby project as a gem (for tool developers)."

  if s.respond_to? :specification_version then
    s.specification_version = 2

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<hoe>, [">= 1.5.1"])
    else
      s.add_dependency(%q<hoe>, [">= 1.5.1"])
    end
  else
    s.add_dependency(%q<hoe>, [">= 1.5.1"])
  end
end
