# traffic-light-simulator
Traffic light simulator implemented in Java using queues

Project Constraints:
Four-way intersection with no turn lanes.
North/South direction is Primary - light is green as much as possible.
  - If no East/West traffic, then North/South light stays green.
  - Minimum length of green light is 30 seconds
  - Simulation always starts with green North/South light
East/West direction:
  - Green light minimum time = 10 seconds, maximum time = 30 seconds
  - Light turns red if no East or West traffic
No yellow lights.
Timing:
  - Separate flow rate (per minute) for cars and trucks
  - Time is simulated.
  - Vehicle arrival time will always be evenly divisible by 60. 
  - The flow rate will not change during a 2-minute simulation.


