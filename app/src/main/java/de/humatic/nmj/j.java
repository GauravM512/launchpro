package de.humatic.nmj;

/* JADX INFO: loaded from: classes.dex */
class j {
    j() {
    }

    public void a(int i, g gVar, int i2) {
        String str;
        try {
            String str2 = gVar.f159b;
            String str3 = gVar.c;
            if (i >= 32 || str3 != null) {
                String str4 = gVar.d;
                int i3 = gVar.b;
                String str5 = gVar.f158a;
                if (i == 1 || i == 2 || i == 6) {
                    if (i == 1) {
                        str = "RTP";
                    } else {
                        str = i == 2 ? "Bluetooth" : "WebSocket";
                    }
                    int numChannels = NMJConfig.getNumChannels();
                    for (int i4 = 0; i4 < numChannels; i4++) {
                        if (NMJConfig.getIP(i4) != null && NMJConfig.getIP(i4).equalsIgnoreCase(str3) && NMJConfig.getPort(i4) == i3) {
                            p.logln(7, "rediscovered " + str + " channel " + str3 + " " + i3);
                            if ((NMJConfig.getRTPState(i4) & 96) == 0) {
                                boolean zM26a = false;
                                if (!str5.equalsIgnoreCase(NMJConfig.getName(i4)) && !NetworkMidiSystem.get().isOpen(-1, i4)) {
                                    NMJConfig.setName(i4, str5);
                                    zM26a = true;
                                }
                                if (i != 2 && !NetworkMidiSystem.get().isOpen(-1, i4)) {
                                    zM26a = NMJConfig.m26a(i4, i2);
                                }
                                if (zM26a) {
                                    NMJConfig.a(i4, 4, 8);
                                }
                                if (NMJConfig.getRTPState(i4) != 16) {
                                    NMJConfig.a(i4, 4, 16);
                                    NMJConfig.m38b(i4, 16);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    }
                    if (NMJConfig.b() >= 4) {
                        String strM35b = NMJConfig.m35b(i2);
                        p.logln(4, "DNS (" + strM35b + "), serviceDiscovered " + str3 + ":" + i3);
                        p.logln(4, "Adding " + strM35b + " / " + str + " channel: " + str5);
                    }
                    if (NMJConfig.f19a != null) {
                        NMJConfig.f19a.putInt("numCh", numChannels + 1);
                        NMJConfig.f19a.commit();
                    }
                    NMJConfig.m20a(numChannels, i);
                    NMJConfig.m38b(numChannels, 16);
                    NMJConfig.setIP(numChannels, str3);
                    NMJConfig.setPort(numChannels, i3);
                    if (str5.length() > 0) {
                        NMJConfig.setName(numChannels, str5);
                    }
                    if (i == 1 || i == 6) {
                        NMJConfig.m26a(numChannels, i2);
                    }
                    NMJConfig.a(numChannels, 4, 8);
                    return;
                }
                if (i == 5 || i == 7) {
                    int i5 = Integer.parseInt(str4);
                    int i6 = (i5 >> 16) & 65535;
                    int i7 = 65535 & i5;
                    int numChannels2 = NMJConfig.getNumChannels();
                    for (int i8 = 0; i8 < numChannels2; i8++) {
                        if (NMJConfig.getMode(i8) == i) {
                            int localPort = (NMJConfig.getLocalPort(i8) >> 16) & 65535;
                            int localPort2 = NMJConfig.getLocalPort(i8) & 65535;
                            if (localPort == i6 && localPort2 == i7 && (i == 7 || NMJConfig.getPort(i8) == (i3 & 255))) {
                                if (NMJConfig.getRTPState(i8) != 32 && NMJConfig.getRTPState(i8) != 16) {
                                    NMJConfig.a(i8, 4, 16);
                                    NMJConfig.m38b(i8, 16);
                                    NMJConfig.h();
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    p.logln(4, "adding USB channel " + str5);
                    NMJConfig.f19a.putInt("numCh", numChannels2 + 1);
                    NMJConfig.f19a.commit();
                    if (str5.length() > 0) {
                        NMJConfig.setName(numChannels2, str5);
                    }
                    NMJConfig.m20a(numChannels2, i);
                    NMJConfig.setIP(numChannels2, str3);
                    int i9 = i3 >> 8;
                    try {
                        if (i9 == 3) {
                            NMJConfig.setIO(numChannels2, -1);
                        } else if (i9 == 2) {
                            NMJConfig.setIO(numChannels2, 1);
                        } else if (i9 == 1) {
                            NMJConfig.setIO(numChannels2, 0);
                        }
                        NMJConfig.setPort(numChannels2, i3 & 255);
                        NMJConfig.setLocalPort(numChannels2, Integer.parseInt(str4));
                    } catch (Exception e) {
                    }
                    NMJConfig.h();
                    NMJConfig.m38b(numChannels2, 16);
                    NMJConfig.a(numChannels2, 4, 8);
                    return;
                }
                if (i == 32) {
                    int numChannels3 = NMJConfig.getNumChannels();
                    for (int i10 = 0; i10 < numChannels3; i10++) {
                        if (NMJConfig.getMode(i10) == 32 && ((gVar.c == null && gVar.f158a.equalsIgnoreCase(NMJConfig.getName(i10))) || (NMJConfig.getIP(i10) != null && NMJConfig.getIP(i10).equalsIgnoreCase(gVar.c) && NMJConfig.getPort(i10) == gVar.b))) {
                            if (gVar.a == -1) {
                                NMJConfig.a(i10, 4, 512);
                                return;
                            }
                            return;
                        }
                    }
                    if (gVar.c != null || gVar.d != null) {
                        NMJConfig.f19a.putInt("numCh", numChannels3 + 1);
                        NMJConfig.f19a.commit();
                        NMJConfig.setName(numChannels3, gVar.f158a);
                        NMJConfig.m20a(numChannels3, i);
                        NMJConfig.setIP(numChannels3, gVar.c);
                        NMJConfig.setPort(numChannels3, gVar.b);
                        NMJConfig.a(numChannels3, 4, 8);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(int i, int i2) {
        if (i == 5) {
            if (i2 == 1 || i2 == 2) {
                if ((NMJConfig.getFlags(-1) & 32) == 0 || (NMJConfig.getFlags(-1) & 256) != 0) {
                    if (NMJConfig.l) {
                        NMJConfig.a(-1, NMJConfig.EV_QUERY_USB, i2);
                    }
                    NMJConfig.l = false;
                    return;
                }
                NMJConfig.m39b(false);
                return;
            }
            if (i2 == 0) {
                if (!NMJConfig.l) {
                    NMJConfig.a(-1, NMJConfig.EV_QUERY_USB, 0);
                }
                NMJConfig.l = true;
                return;
            }
            return;
        }
        if (i == 7) {
            if (i2 == 1 || i2 == 2) {
                if (NMJConfig.l) {
                    NMJConfig.a(-1, NMJConfig.EV_QUERY_USB, i2);
                }
                NMJConfig.l = false;
                return;
            } else {
                if (i2 == 0) {
                    if (!NMJConfig.l) {
                        NMJConfig.a(-1, NMJConfig.EV_QUERY_USB, 0);
                    }
                    NMJConfig.l = true;
                    return;
                }
                return;
            }
        }
        if (i == 2) {
            NMJConfig.a(-1, 528, i2);
        }
    }
}
