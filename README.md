# traffic-light-simulator
Traffic light simulator implemented in Java using queues

Project Constraints:
I. Intersection Test Setup: 
Four-way intersection with no turn lanes. Four sensors are pointed in each direction to monitor if traffic exists on that side of the intersection.

II. Traffic Light Priority Setup:
North/South:
a.	Primary – meaning we want this to be “green” as much as possible. If East/West does not have traffic, then North/South will STAY “green”
b.	Will always be “green” together
c.	Minimally “green” for 30 seconds each time it turns green
d.	There is no maximum value for how long it stays green
e.	The simulation will ALWAYS start with North/South being “green”

East/West:
a.	Maximally “green” for 30 seconds
b.	Minimally “green” for 10 seconds
c.	Will turn “red” early if no traffic on either East/West side

Ignore yellow as a whole. Assume no delay in car movement after light turns green.

III. Flow Rate:
Each side will have separate flow rates (per minute) for cars and for trucks. NOTE: Time is simulated. Vehicle arrival time will always be evenly divisible by 60. The flow rate will not change during a 2-minute simulation.


