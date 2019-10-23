/* -*- Mode:C++; c-file-style:"gnu"; indent-tabs-mode:nil; -*- */
// GPLv2 Licence ...

#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"
#include "ns3/netanim-module.h"
#include "ns3/gnuplot-helper.h"

using namespace ns3;
NS_LOG_COMPONENT_DEFINE ("FirstScriptExample");

int main (int argc, char *argv[])
{
LogComponentEnable ("UdpEchoClientApplication", LOG_LEVEL_INFO);
LogComponentEnable ("UdpEchoServerApplication", LOG_LEVEL_INFO);

NodeContainer nodes;
nodes.Create (3);
PointToPointHelper pointToPoint;
pointToPoint.SetDeviceAttribute ("DataRate", StringValue ("5Mbps"));
pointToPoint.SetChannelAttribute ("Delay", StringValue ("1ms"));


PointToPointHelper pointToPoint2;
pointToPoint2.SetDeviceAttribute ("DataRate", StringValue ("10Mbps"));
pointToPoint2.SetChannelAttribute ("Delay", StringValue ("5ms"));


NetDeviceContainer devices;
devices = pointToPoint.Install (nodes);

InternetStackHelper stack;
stack.Install (nodes);

Ipv4AddressHelper address;
address.SetBase ("10.1.1.0", "255.255.255.0");

NetDeviceContainer devices;
devices = pointToPoint.Install (nodes.Get (0), nodes.Get (1));
Ipv4InterfaceContainer interfaces = address.Assign (devices);


devices =pointToPoint2.Install (nodes.Get (1), nodes.Get (2));
address.SetBase ("10.1.3.0", "255.255.255.0");

interfaces = address.Assign (devices);

Ipv4GlobalRoutingHelper::PopulateRoutingTables ();
UdpEchoServerHelper echoServer (9);




//----------------------------------------------------------
ApplicationContainer serverApps = echoServer.Install (nodes.Get (2));
serverApps.Start (Seconds (1.0));
serverApps.Stop (Seconds (10.0));

UdpEchoClientHelper echoClient (interfaces.GetAddress (1), 9);
echoClient.SetAttribute ("MaxPackets", UintegerValue (8));
echoClient.SetAttribute ("Interval", TimeValue (Seconds (1.0)));
echoClient.SetAttribute ("PacketSize", UintegerValue (2048));


ApplicationContainer clientApps = echoClient.Install (nodes.Get (0));
clientApps.Start (Seconds (1.0));
clientApps.Stop (Seconds (10.0));

pointToPoint.EnablePcapAll("Rohin");


AnimationInterface anim ("Rohin.xml");
  AsciiTraceHelper ascii;
  pointToPoint.EnableAsciiAll (ascii.CreateFileStream ("Rohin.tr"));
  pointToPoint.EnablePcapAll ("Rohin");
anim.UpdateNodeSize(0,3,3);//size of node1 Updated
anim.UpdateNodeSize(1,3,3);//size of node2 Updated
anim.SetConstantPosition (nodes.Get(0), 0.0, 0.0);
anim.SetConstantPosition (nodes.Get(1), 1.5, 1.5);
anim.SetConstantPosition (nodes.Get(2), 3.0, 3.0);
// for wireshark grep

  pointToPoint.EnablePcapAll ("Rohin");

//for data file
  //probeType = "ns3::Ipv4PacketProbe";
  //tracePath = "/NodeList/*/$ns3::Ipv4L3Protocol/Tx";
  GnuplotHelper plotHelper;
  plotHelper.ConfigurePlot ("Rohin",
                          "Packets Count vs. Time",
                          "Time (Sec)",
                          "Packet Count",
                          "png");
  plotHelper.PlotProbe ("ns3::Ipv4PacketProbe",
                      "/NodeList/*/$ns3::Ipv4L3Protocol/Tx",
                      "OutputBytes",
                      "Packet Count",
                      GnuplotAggregator::KEY_BELOW);

pointToPoint.EnablePcapAll("Rohin1");
pointToPoint.EnablePcapAll("Rohin2");


Simulator::Run ();
  Simulator::Destroy ();
  return 0;
}

