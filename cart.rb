class Cart

	attr_reader: items
	include ItemContainer
	
	#add comment to mehtod initialize
	
	def initialize(owner)
		@items = Array.new
		@owner = owner
	end
end