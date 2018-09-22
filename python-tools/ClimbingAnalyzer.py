import matplotlib.pyplot as plt
import random
from file_processor import plot_a_list_graph
import os


class ClimbingAnalyzer:
    def __init__(self):
        pass

    def generate_blocks(self, fitness_value_lists):
        blocks = []

        step = 0
        for a_fitness_value_list in fitness_value_lists:
            for fitness_value in a_fitness_value_list:
                blocks.append((step, fitness_value))
            step += 1

        return blocks

    def plot_blocks(self, blocks, to_save=False, save_path='', save_name=''):
        starting_fit = blocks[0][1]

        x_s_g = [x[0] for x in blocks if x[1] <= starting_fit]
        y_s_g = [x[1] for x in blocks if x[1] <= starting_fit]

        x_s_r = [x[0] for x in blocks if x[1] > starting_fit]
        y_s_r = [x[1] for x in blocks if x[1] > starting_fit]

        plt.scatter(x_s_g, y_s_g, marker='.', color='g')
        plt.scatter(x_s_r, y_s_r, marker='.', color='r')

        plt.xlabel('No. of Edges Removed')
        plt.ylabel('Fitness')
        plt.axhline(y=blocks[0][1], color='blue', linestyle='--', label='original fitness')
        if not to_save:
            plt.show()
        else:
            plt.savefig(save_path + os.sep + save_name, dpi=200)
            plt.gcf().clear()

    def generate_random_path(self, blocks):
        rtn_path = []
        path_dict = {}
        for x, y in blocks:
            if not path_dict.has_key(x):
                path_dict[x] = [y]
            else:
                path_dict[x].append(y)
        path_dict = sorted(path_dict.items())
        for x, fitness_values in path_dict:
            rtn_path.append(random.choice(fitness_values))
        return rtn_path

# omega = ClimbingAnalyzer()
# blocks = omega.generate_blocks(
#     [
#         [0.9445605878139494],
#         [0.9444801447088411, 0.9452741514683078],
#         [0.9412268631774212, 0.9418271895471717, 0.9418482252230311, 0.9422576472069848, 0.9426087171355906,
#          0.943164105518687, 0.9432339987702048, 0.9433343095873782, 0.9433745921158951, 0.9433810451392678,
#          0.9434154571222202, 0.9435739490131833, 0.9436670058051411, 0.943737699159176, 0.9442261038614813,
#          0.9443910141346565, 0.9443973541807555, 0.9447888631725385, 0.9448800325154999],
#         [0.9132098101559332, 0.9133184482458925, 0.9137524056250225, 0.9139262240897766, 0.9139533766995007,
#          0.9139580234330213, 0.914047590164552, 0.9141760128509065, 0.9144992140901085, 0.9151462142361455,
#          0.9158529548716299, 0.9160620254190182, 0.916161220130788, 0.9161934933809184, 0.9170633267753079,
#          0.9173832335018821, 0.9174711003985411, 0.9192135235327838, 0.9194500415505474, 0.9200731730287162,
#          0.9212939238373159, 0.9217752481254593, 0.9219834497238677, 0.9221928212424801, 0.9222785650831438,
#          0.9223596809733918, 0.9226438443071062, 0.9234769042368598, 0.923590735027831, 0.9239465998866719,
#          0.9246655999150164, 0.9247614847449868, 0.9252277692175861, 0.9253769372840639, 0.9258164273352608,
#          0.9260331195256808, 0.9263908916191694, 0.9274513196578416, 0.9275999706641256, 0.9281731738223731,
#          0.9295749284041042, 0.9307080693951342, 0.930754360950875, 0.9321009640427225, 0.9334590756210994,
#          0.9335271131130071, 0.9338668602608786, 0.9338755661132159, 0.9341051593579281, 0.9341982058452107,
#          0.9342276044704393, 0.9345777681851852, 0.9348618883575637, 0.9349762049921915, 0.9351340005807889,
#          0.9360043695093161, 0.9360338432218497, 0.9360522000875403, 0.9361046298425195, 0.9361729794006696,
#          0.9365806518341959, 0.9367362443919391, 0.9368338086205983, 0.9368711372418264, 0.9370519626101498,
#          0.939174091498923, 0.9393971781612105, 0.9402604638509114, 0.9404196572049582, 0.9405330651090191,
#          0.9407618742226243, 0.9407683272927639, 0.9410924940479748, 0.9411347579263816, 0.941187087232211,
#          0.9416213065908405, 0.9416861579919467, 0.9418703242686088, 0.9418705880157919, 0.9425024465234295,
#          0.9426325037972125, 0.9426387995038035, 0.9426717271376428, 0.9426765689050834, 0.9428400266623396,
#          0.9428453324080803, 0.9428869664461029, 0.943073362268642, 0.9430798334763013, 0.9431367435912926,
#          0.9433230550846051, 0.9434204749536269, 0.9434534152406922, 0.9435796672165848, 0.9435854453120931,
#          0.943664626779703, 0.9437407875546279, 0.9438048652187847, 0.9438262160548225, 0.9438304858974442,
#          0.9439009740971782, 0.9439123440306187, 0.9441395148744978, 0.944151779089834, 0.9442037290355845,
#          0.9442317559756563, 0.9443180851318143, 0.9443185554337216, 0.9444773310665778, 0.9445570758570027,
#          0.9446342512655583, 0.944798075873972, 0.944870721305713, 0.9448818946945428],
#         [0.8797600253948114, 0.8797811328759024, 0.8797811328759024, 0.8797811328759024, 0.8798274522522369,
#          0.8798330269433974, 0.8798397805239251, 0.8800506775179355, 0.8800577699418104, 0.8800855220097028,
#          0.8801222360246792, 0.8802611521337603, 0.8803130977190219, 0.8803151087532239, 0.8803757566031234,
#          0.880452010240822, 0.880466318063372, 0.880505316788039, 0.8805764301195558, 0.8805876938220791,
#          0.8806035242271117, 0.8806337850115697, 0.8806581029635507, 0.8806753347646579, 0.880750319486755,
#          0.880800260394397, 0.880805956174012, 0.880810421531298, 0.8808718519491004, 0.8809061815509558,
#          0.8809081708699534, 0.880965750082874, 0.8809925281341484, 0.8809925281341484, 0.881011198376827,
#          0.881029497732015, 0.8810423291702669, 0.881076675982212, 0.8810961427886614, 0.8811489712081928,
#          0.8811977887629072, 0.8812084809479943, 0.881258382077027, 0.8812786203290977, 0.8813181828790362,
#          0.8814107503246458, 0.8814475857100667, 0.8814475857100667, 0.881469969758365, 0.8815614215043507,
#          0.8815625180366145, 0.8815776444947072, 0.8816283625827777, 0.8818337946383145, 0.8818337946383145,
#          0.8818711538714628, 0.8819003586626564, 0.8819718807374153, 0.8819784030452128, 0.8820110662158549,
#          0.8820267327105507, 0.882040936853727, 0.8820560533294729, 0.8821348844256045, 0.8821823024818909,
#          0.8822256321190999, 0.8822936658558571, 0.8823023513738022, 0.8824392032091097, 0.8824392032091097,
#          0.8825116552737423, 0.882672915571229, 0.8828807155582764, 0.8831646819279868, 0.883315457112666,
#          0.883392655795354, 0.8834735118104737, 0.8835717344110354, 0.8836194518425922, 0.8837510654465531,
#          0.8838370720639621, 0.8839235457744754, 0.8840516152932372, 0.8840791161229298, 0.8843763492061085,
#          0.8848436437845779, 0.8848571562126438, 0.8850730747067639, 0.8852795837082634, 0.885319493792009,
#          0.8853525277721692, 0.8854657404134907, 0.8855795286705852, 0.8856278730870899, 0.8857269337589619,
#          0.8858722893888737, 0.8861895175580135, 0.8863960931678457, 0.8866980371230613, 0.886902793026976,
#          0.886902793026976, 0.886916648350093, 0.8872310046212543, 0.8878848702244788, 0.8879100436202201,
#          0.8879213864226987, 0.8879213864226987, 0.8882169009634395, 0.8883124544041279, 0.8886049353062268,
#          0.8888211385515229, 0.8891054260111746, 0.8891780889458922, 0.8892817081431226, 0.8893258922052953,
#          0.889541574825094, 0.8898507285809083, 0.8899958435802584, 0.8901760712157705, 0.89041666205111,
#          0.8905095488075098, 0.8905191659278744, 0.8905872986881368, 0.89100531223213, 0.8911863570256184,
#          0.8914633312613078, 0.8914670952999018, 0.8915275522920404, 0.8915647043418986, 0.8917733007973668,
#          0.8917735405615164, 0.8920474951245287, 0.8921931562900756, 0.8922594198169292, 0.8922982586604169,
#          0.8924666919516466, 0.8929008879141473, 0.8930075690714263, 0.8935071246031875, 0.8936023638431656,
#          0.8937072037161681, 0.8939701058698357, 0.8940295472783877, 0.8940426647378954, 0.8943143622867858,
#          0.8944678575418474, 0.8946318959332888, 0.8946419605167018, 0.8947506232368676, 0.8950386989496425,
#          0.8951948347883223, 0.8952171594602226, 0.8954989826208228, 0.8956575539357706, 0.8957981250127962,
#          0.896337479865453, 0.8964598318224113, 0.8965737027802461, 0.8965949466400877, 0.8968751051713428,
#          0.8969114742975655, 0.8971214750003638, 0.8972137877910773, 0.8972531703253599, 0.897480563050095,
#          0.8981293098895688, 0.8990936672554937, 0.8990954581531672, 0.8992560101939581, 0.8994249233862976,
#          0.8997031936605544, 0.8997762686782175, 0.8999942466292681, 0.8999984792097764, 0.9001493043436481,
#          0.9007899297008439, 0.9008801403658939, 0.9009050814189347, 0.9009267813576174, 0.9012699584162055,
#          0.9018057695015729, 0.9018842588442234, 0.9019278013212695, 0.9025696198767073, 0.9028971444828844,
#          0.9032625993798338, 0.9034442123211938, 0.9036804929382487, 0.9037338934356138, 0.9039757762385425,
#          0.904029426582154, 0.9041648854090691, 0.9042356943493952, 0.9042758069343653, 0.9046983713504397,
#          0.9052739696052456, 0.9053883390077533, 0.9058789403088656, 0.9059962604151044, 0.9060234108822087,
#          0.9060235752588918, 0.9062918639753376, 0.9064655121772793, 0.9067146412153408, 0.9069135133195684,
#          0.907017816355248, 0.9070310986978518, 0.907411824057136, 0.9075987525109575, 0.9076188043143184,
#          0.9077056832665318, 0.9077753661945149, 0.9077794597627131, 0.9081651731096191, 0.9085353452409293,
#          0.9085817024851041, 0.908672988678608, 0.9088543058882632, 0.9090808194123745, 0.909096344369813,
#          0.9093581632197131, 0.9094292427079598, 0.9095786112560612, 0.909584244068754, 0.9096072321660237,
#          0.909873981573459, 0.9099872258670763, 0.9100497912149265, 0.9104932849347106, 0.9106444318425837,
#          0.9106705996236, 0.9107072983494167, 0.9109037444663656, 0.9109894729194147, 0.9110900627503902,
#          0.9111307253336391, 0.9112733842479608, 0.9114610787832949, 0.9118711246507808, 0.911877825323667,
#          0.9119055965493714, 0.9120135749635486, 0.9121221961272212, 0.9123641938991248, 0.9123717333811803,
#          0.9125039151894042, 0.9125929120502425, 0.9127734323599859, 0.9131278493893764, 0.9134973378566107,
#          0.9135700007913282, 0.9139190972923592, 0.9139262240897766, 0.9139604548486193, 0.9141466369189895,
#          0.9143528349108232, 0.9143598614004735, 0.9144277339180783, 0.9144566917630179, 0.9145598260911953,
#          0.914610190533357, 0.9147704064199021, 0.9150592677957419, 0.9150841627831094, 0.9151950044245444,
#          0.9152379416890152, 0.915403043234953, 0.9155038523500938, 0.9156302438697693, 0.915703330387641,
#          0.9158543627148477, 0.9160463461934053, 0.9162899822945861, 0.916631792735209, 0.9166658559679319,
#          0.9167311381230604, 0.9167545564291928, 0.9171581572389453, 0.9171790789670227, 0.9172663379918446,
#          0.9176038725736289, 0.917634625033583, 0.9180330497500235, 0.9186022679141136, 0.9194216316376987,
#          0.9195996591422114, 0.9197136272498487, 0.9198972799707539, 0.9200285053102966, 0.9201324731295497,
#          0.9201425080709611, 0.9201556358339353, 0.9202319668459495, 0.9204469846837915, 0.9204908261693179,
#          0.9207528928388369, 0.9208725615148435, 0.9209130908559909, 0.9211557226350521, 0.9212987408396296,
#          0.9216372230904318, 0.9224567034418404, 0.9227539755334826, 0.922867324744464, 0.923109557341064,
#          0.9232072432369474, 0.923756684202815, 0.9238535394288353, 0.9238937297456162, 0.9239267184292146,
#          0.924026581012903, 0.9243671311545679, 0.9243693214695505, 0.924745709023556, 0.9247539700515542,
#          0.9249886268606258, 0.925171915346645, 0.9253534849625424, 0.9253733476258892, 0.9256292214087358,
#          0.9256713195335616, 0.9260219480266798, 0.9260420965499159, 0.9261701700497463, 0.9262965399339498,
#          0.9263407565487782, 0.9264129922114566, 0.9266528372777965, 0.9267718970322627, 0.926944176598935,
#          0.9270802368595398, 0.9271444348929336, 0.92736758185885, 0.9276651510430856, 0.9277402624259682,
#          0.9280405369782783, 0.9280742940632287, 0.9281852072716431, 0.9285271215556479, 0.9289129293316487,
#          0.9292773490874406, 0.9293059709348672, 0.9302724106721689, 0.9307608698910256, 0.9309321514825399,
#          0.9311505933911806, 0.9316617014393274, 0.9320756265551695, 0.9322663597800225, 0.932434312586569,
#          0.9324819938924016, 0.9326335992467146, 0.9327959091340807, 0.9328974716629064, 0.9329929358045985,
#          0.9330234856820959, 0.9334030001024094, 0.9334340243766907, 0.9335417272831945, 0.9335775197048968,
#          0.933711722831637, 0.9337697745681204, 0.9338019768740323, 0.9338944257845968, 0.9340774355830057,
#          0.934191754556638, 0.9342102740223405, 0.9342110475116943, 0.9342180054952998, 0.9342839003879944,
#          0.9343977793039111, 0.9344505149879001, 0.9347301885287559, 0.9347337234268432, 0.9348043581980813,
#          0.9348641061053717, 0.9349700983237792, 0.93514057796326, 0.9351517240798455, 0.9351710248227605,
#          0.9352107961755732, 0.9352643570525541, 0.9353724982390568, 0.9354821062487606, 0.9356603677062096,
#          0.9357075109184847, 0.9357241213629386, 0.9358023322161978, 0.9358039942245846, 0.9360984920806736,
#          0.9361238074411351, 0.9363426460613508, 0.9367527896207615, 0.9368161775355764, 0.9368208308997426,
#          0.9370152676607637, 0.9370853719390155, 0.9372359331524927, 0.9374455536928499, 0.9375700878051737,
#          0.937768833526498, 0.9379613408281613, 0.9380030521853386, 0.9380170878564099, 0.9381718721535695,
#          0.9385744778406728, 0.9388559825938863, 0.9397777014245066, 0.9397948733689279, 0.9401438006152818,
#          0.9401745988506058, 0.9402682748642646, 0.9406354063800568, 0.9407308961304216, 0.9408453545191955,
#          0.9409862919163983, 0.941263445453062, 0.9413144466623172, 0.941487966197881, 0.941512151100472,
#          0.94154496348997, 0.9416707751215023, 0.9416764738044121, 0.9416797620278834, 0.9417619209173056,
#          0.9418837478438331, 0.9419028665168265, 0.9419087447893086, 0.9420241240332918, 0.9420673992403515,
#          0.9420849664879514, 0.9420903230857627, 0.9421006893290198, 0.9421445743154082, 0.942248848008049,
#          0.9423409452591647, 0.9423559073909444, 0.9424064942072828, 0.9426499374972047, 0.9427054683382495,
#          0.9427922254734595, 0.9428132494344481, 0.9428135598373422, 0.942821197481355, 0.942910552862088,
#          0.9429191025547368, 0.9429649851588566, 0.9430798334763013, 0.9430906488357415, 0.9431015568841274,
#          0.9431634513546567, 0.943232794899044, 0.9432955395781873, 0.943339095395168, 0.9435818114930549,
#          0.9436603444495195, 0.943720593176399, 0.943730096714201, 0.9438080908810123, 0.9439040535300107,
#          0.9439123440306187, 0.9439726763330532, 0.943984030415833, 0.9440668457024811, 0.9440753483218528,
#          0.944153665812514, 0.9442185674122054, 0.9443157336024166, 0.9443185554337216, 0.9443992326666563,
#          0.9444754552786636, 0.9444773310665778, 0.9444796757717646, 0.944558714779473, 0.9447217081519924,
#          0.9447921269280631, 0.9447972555567925, 0.9448632719603872, 0.9448763080945002, 0.9448800325154999,
#          0.945039992366754, 0.9451148731317891, 0.9452806220144125, 0.9461431498293879, 0.9463042061589311],
#
#     ]
# )
# omega.plot_blocks(blocks)

# a_random_path = omega.generate_random_path(blocks)
# plot_a_list_graph(
#     a_random_path,
#     'fitness')