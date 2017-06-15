class Cart

	attr_reader: items
	include ItemContainer
	
	def initialize(owner)
		@items = Array.new
		@owner = owner
	end
end