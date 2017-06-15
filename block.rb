class Blocks

	def in_ruby_version(*version)
	RUBY_VERSION = ~/^#{version}/ || (version == 'jruby' && defined?(JRUBY_VERSION)
	end
end