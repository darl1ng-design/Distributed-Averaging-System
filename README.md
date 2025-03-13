# Distributed_UDP_System
The application is a Distributed Averaging System (DAS) that runs as a Java program using UDP communication. It operates in two modes:

Master Mode:

Starts if the specified UDP port is available.
Stores incoming numbers and handles three cases:
  - Regular number (>0 or -1): Prints and stores it.
  - Zero (0): Computes and broadcasts the average of all received numbers.
  - Minus one (-1): Broadcasts -1 and terminates.

Slave Mode:

Starts if the port is already in use (meaning a master is active).
Sends the provided number to the master and exits.


This system facilitates distributed computation of an average value using UDP communication within a local network.
