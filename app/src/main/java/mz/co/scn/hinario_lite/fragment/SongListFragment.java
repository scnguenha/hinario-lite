package mz.co.scn.hinario_lite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mz.co.scn.hinario_lite.R;
import mz.co.scn.hinario_lite.activity.SongDetailActivity;
import mz.co.scn.hinario_lite.adapter.SongAdapter;
import mz.co.scn.hinario_lite.model.Category;
import mz.co.scn.hinario_lite.model.Song;
import mz.co.scn.hinario_lite.util.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongListFragment#} factory method to
 * create an instance of this fragment.
 */
public class SongListFragment extends BaseFragment implements View.OnClickListener, SongAdapter.OnSongSelectedListener {
    private static final String TAG = "CantemosFragment";
    private String book;
    private Bundle bundleRecyclerViewState;
    private Parcelable recyclerViewState = null;
    private static String STATE = SongListFragment.class.getName();

    private RecyclerView recyclerView;

    private FirebaseFirestore firestore;
    private Query query;

    private SongAdapter adapter;

    public SongListFragment() {
        // Required empty public constructor
    }

    public SongListFragment(String book) {
        this.book = book;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cantemos, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);

        initFireStore();
        initRecyclerView();

        v.findViewById(R.id.fab_addsongs).setOnClickListener(v1 -> {
            generateCantemosSongs();
            generateDataRhonga();
            generateDataChangana();
        });

        return v;
    }

    private void initFireStore() {
        firestore = FirebaseFirestore.getInstance();
        // Get all songs from #book
        query = firestore.collection(book).orderBy("number");
    }

    private void initRecyclerView() {
        if (query == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        adapter = new SongAdapter(query, this) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query return empty.
                if (getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(getView().findViewById(android.R.id.content), "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    protected void generateCantemosSongs() {
        List<Song> songs = new ArrayList<>();

        songs.add(new Song("Para altos Montes", 1, "S. H. 21", "I\n" +
                "\n" +
                "Para altos montes olharei\n" +
                "Donde vem a Salvação? (Bis)\n" +
                "Do meu divino Protector\n" +
                "Virá consolação (3x)\n" +
                "\n" +
                "II\n" +
                "\n" +
                "No braço forte esperarei\n" +
                "Do meu Amparador (bis)\n" +
                "Por Ele a terra feita está\n" +
                "Dos céus é o Senhor (3x)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "O pé dos servos de Jesus\n" +
                "Nem sempre tremerá (Bis)\n" +
                "Aquele que guarda a Israel\n" +
                "Não adormecerá (3x)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Do crente á mão direita está\n" +
                "Quem o protege bem (bis)\n" +
                "Nem sol nem lua o ferirá\n" +
                "Desastres não lhe vêm (3x)\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Os inimigos dos fiéis\n" +
                "Os querem assustar (bis)\n" +
                "O protegido por Jesus\n" +
                "Sem mede deve andar (3x)\n"));

        songs.add(new Song("Jesus Pastor Amado", 2, "S. H. 60; T. V. 283", "I\n" +
                "\n" +
                "Jesus pastor amado,\n" +
                "Juntos eis-nos aqui:\n" +
                "Concede que sejamos,\n" +
                "Um corpo só em ti\n" +
                "Contendas e malícias,\n" +
                "Que longe de nós vão;\n" +
                "Nenhum desgosto impeça\n" +
                "A nossa santa união.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Uma só família somos,\n" +
                "Família de Jesus;\n" +
                "Uma morada temos,\n" +
                "Numa celeste luz.\n" +
                "A mesma fé nos une,\n" +
                "Num só divino amor;\n" +
                "E com o mesmo gozo,\n" +
                "Servimos ao Senhor.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Num só caminho estreito\n" +
                "Deus mesmo nos conduz,\n" +
                "Não temos esperança;\n" +
                "Senão num só Jesus,\n" +
                "Sua preciosa morte\n" +
                "A todos vida traz;\n" +
                "E pelo mesmo sangue\n" +
                "Nos vem a mesma Paz.\n" +
                "\n" +
                "IV\n" +
                "Pois sendo resgatados,\n" +
                "Por um só salvador\n" +
                "Devemos ser unidos,\n" +
                "Pelo mais forte amor;\n" +
                "Olhar com simpatia\n" +
                "Os erros dum irmão;\n" +
                "E todos ajudá-lo,\n" +
                "Com branda compaixão.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Jesus suave e meigo,\n" +
                "Ensina-nos a amar;\n" +
                "E como tu sejamos\n" +
                "Prontos a perdoar;\n" +
                "Ah! Quanto carecemos,\n" +
                "Auxílio do Senhor!\n" +
                "Unidos levantemos\n" +
                "Rogos por esse amor.\n" +
                "\n" +
                "VI\n" +
                "\n" +
                "Se tua Igreja toda,\n" +
                "Andar em santa união,\n" +
                "Então será bendito\n" +
                "O nome de \"Cristão\"\n" +
                "Assim, o que pediste,\n" +
                "Em nós se cumprirá\n" +
                "E todo o mundo inteiro,\n" +
                "A ti conhecerá!\n"));

        songs.add(new Song("Filhos do Celeste Rei", 3, "S. H. 89; T. V. 15", "I\n" +
                "\n" +
                "Filho do celeste Rei,\n" +
                "Sempre a Cristo bendizei;\n" +
                "Vosso Salvador louva,\n" +
                "I Suas obras exaltai.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Por caminhos viajais,\n" +
                "Já trilhados pelos mais\n" +
                "Santa via, que conduz,\n" +
                "Lá, para onde reina a luz.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ide, pois, não demoreis,\n" +
                "Apressar-vos, sim deveis;\n" +
                "O que vos espera ali,\n" +
                "Não conhece igual aqui.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Pois espera-vos Jesus,\n" +
                "Esse que, na horrenda cruz,\n" +
                "Vossa sorte a si chamou,\n" +
                "Nossa punição tomou\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Tendes Pai ali também,\n" +
                "Pai que muito amor vos tem\n" +
                "Seus filhinhos Ele traz,\n" +
                "Cheios de alegria e paz\n" +
                "\n" +
                "VI\n" +
                "\n" +
                "Eis com estenditos mãos;\n" +
                "Coros Santos dos irmãos\n" +
                "Parabéns vos querem dar,\n" +
                "Nesse alegre e doce lar.\n"));

        songs.add(new Song("A Luz do Mundo", 4, "S. H. 111; T. V. 42", "I\n" +
                "\n" +
                "Luz do mundo! Jesus Cristo!\n" +
                "Vem, dissipa as ilusões\n" +
                "Tira o véu dos nossos olhos,\n" +
                "Ilumina os corações (bis)\n" +
                "Para ver-te! Cumpre nossas orações.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Nos desertos deste mundo,\n" +
                "Onde reina satanás\n" +
                "Resplandeça o Evangelho,\n" +
                "Brilhem tua graça e paz (bis)\n" +
                "Luz divina, vença toda a luz falaz!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Onde as trevas do pecado\n" +
                "Obscurecem teu amor\n" +
                "Rei e divinal ensino,\n" +
                "Do benigno Salvador; (bis)\n" +
                "Manifesta tua glória, ó Senhor\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Luz dos homens? Luz da vida\n" +
                "Brilha com poder nos teus!\n" +
                "Esclarece as suas almas,\n" +
                "Mostra-lhes o grande Deus (bis)\n" +
                "Luz do mundo! És o resplendor dos Céus!\n"));

        songs.add(new Song("Filhos de Jerusalém", 5, "S. H. 119; T. V. 327", "I\n" +
                "\n" +
                "Filhos de Jerusalém,\n" +
                "Davam a Jesus louvor:\n" +
                "Cantaremos nós também,\n" +
                "Seu excelso e doce amor!\n" +
                "\n" +
                "CORO\n" +
                "\n" +
                "Ouve! Os meninos dão louvor, (bis)\n" +
                "Aleluia Aleluia Aleluia, ao Salvador.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Graças ao divino Rei,\n" +
                "Que no mundo veio viver!\n" +
                "Graças pela santa lei,\n" +
                "Que declara o seu querer! (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ah! Quem poderá dizer,\n" +
                "Quantas nossas culpas são\n" +
                "Merecemos padecer,\n" +
                "Pena de condenação! (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Grande é o nosso Salvador!\n" +
                "Toda a dívida pagou,\n" +
                "Pela morte o Bom Pastor\n" +
                "Seu rebanho resgatou (Coro)\n"));

        songs.add(new Song("Éis-Me Ó Salvador", 6, "S. H. 125; T. V. 123", "I\n" +
                "\n" +
                "Eis-me, ó Salvador! Aqui\n" +
                "Corpo e alma oferta a ti\n" +
                "Servo inútil, sem valor,\n" +
                "Mas pertenço ao meu Senhor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Fraco em obra e no pensar,\n" +
                "Mui propenso a tropeçar,\n" +
                "Salvo estou por teu amor,\n" +
                "E me ti, Senhor!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Subjugado em todo o ser,\n" +
                "Me submeto ao teu poder;\n" +
                "Grande o preço do perdão,\n" +
                "Inteira a consagração.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Eu, remido pecador\n" +
                "Me dedico ao Redentor\n" +
                "Teu, é este coração\n" +
                "Teu em plena sujeição.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Toma-me Senhor Jesus,\n" +
                "Faz-me andar contigo em Luz,\n" +
                "Sem reserva, sem temor,\n" +
                "Teu cativo, ó Salvador!\n"));

        songs.add(new Song("Ouve a voz Divina Clama", 7, "S. H. 136; T. V. 107", "I\n" +
                "\n" +
                "Ouve a voz divina clama,\n" +
                "Quem irá a trabalhar?\n" +
                "Ricos campos nos convidam\n" +
                "Hoje entremos a ceifar!\n" +
                "Alto e forte o mestre chama;\n" +
                "Galardão de oferta ali.\n" +
                "Quem responderá dizendo:\n" +
                "\"Manda-me! Estou pronto aqui!\"\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Corre! Aponta aos pecadores\n" +
                "O benigno Salvador \n" +
                "Vai! Conduze os cordeirinhos\n" +
                "Ao regaço do Pastor.\n" +
                "Leva às almas doloridas\n" +
                "Novas de consolação\n" +
                "Vai! Publica a todo o mundo:\n" +
                "\"Em Jesus há Salvação!\"\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ah! Não digas, ocioso:\n" +
                "\"Eu não tenho que fazer!\"\n" +
                "Eis os povos que falecem!\n" +
                "Multidões a perecer\n" +
                "Olha o mestre que suplica\n" +
                "Ouve a voz chamando ali\n" +
                "Oh! Responde sem demora\n" +
                "\"Manda-me! Estou pronto aqui!\"\n"));

        songs.add(new Song("Vinde, Meninos", 8, "S. H. 137; T. V. 105", "I\n" +
                "\n" +
                "Vinde meninos vinde a Jesus!\n" +
                "Ele ganhou-vos bênção na cruz\n" +
                "Os pequeninos Ele conduz\n" +
                "Vinde ao Salvador.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Que alegria! Sem pecado ou mal,\n" +
                "Reunir-nos todos afinal,\n" +
                "Na Santa Pátria Celestial,\n" +
                "Com nosso Salvador.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Já, sem demora, hoje convém\n" +
                "Ir caminhando à glória além;\n" +
                "Jesus vos chama, quer vosso bem,\n" +
                "Vinde ao Salvador (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ama os meninos Jesus o diz;\n" +
                "Quer receber-vos no bom país\n" +
                "Quer conceder-vos vida feliz,\n" +
                "Vindo ao Salvador (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Eis a chamada, \"oh! Vinde a mim!\"\n" +
                "Outro não há que vos ame assim;\n" +
                "Seu é o amor que nunca tem fim,\n" +
                "Vinde ao Salvador. (Coro)\n"));

        songs.add(new Song("Oh doce é meu descanso", 9, "S. H. 138; T. V. 142", "I\n" +
                "\n" +
                "Oh! Doce é meu descanso, No forte redentor!\n" +
                "Perfeitamente a salvo, Na graça do Senhor!\n" +
                "Por mim Jesus morreu!\n" +
                "Eu não perecerei!\n" +
                "Por mim obedeceu\n" +
                "A Santa externa lei!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "A mim Jesus abriu\n" +
                "Seu grande coração!\n" +
                "Em seu amor firmado,\n" +
                "Já tenho a salvação\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Salvo por meu Amado!\n" +
                "Salvo da perdição\n" +
                "Salvo do triste império,\n" +
                "Da morte e tentação,\n" +
                "Livre das incertezas,\n" +
                "Do mundo e Satanás,\n" +
                "Livre de todo o medo\n" +
                "Gozo de estável paz, (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ainda por curtos dias\n" +
                "Caminho em meia-luz,\n" +
                "Minha alma se aquieta\n" +
                "A voz do meu Jesus\n" +
                "Cedo esta noite acaba,\n" +
                "Cedo Ele voltará\n" +
                "Reia a celeste aurora,\n" +
                "Jesus não tardará, (Coro)\n"));

        songs.add(new Song("Vem Espírito Divino", 10, "S. H. 139", "I\n" +
                "\n" +
                "Vem! Espirito Divino\n" +
                "Grande ensinador\n" +
                "Vem! Descobre ás nossas almas,\n" +
                "Cristo, o Salvador.\n" +
                "Mestre! Mestre! Ouve com favor,\n" +
                "Em poder e graça insigne\n" +
                "Obre o teu amor\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Vem! Demole os alicerces\n" +
                "Da enganosa paz\n" +
                "Aos errados concedendo,\n" +
                "Salvação Veraz! (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Vem! Reveste a tua Igreja\n" +
                "De energia e luz\n" +
                "Vem! Atrai os transviados\n" +
                "Ao Senhor Jesus! (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Maravilhas soberanas,\n" +
                "Outros povos vêem\n" +
                "Oh! Derrama a mesma bênção,\n" +
                "Sobre nós também! (Coro)\n"));

        songs.add(new Song("Com Jesus há Morada", 11, "S. H. 140; T. V. 223", "I\n" +
                "\n" +
                "Com Jesus há morada feliz\n" +
                "Prometida e segura nos Céus;\n" +
                "Avistamos o santo país\n" +
                "Pela fé na palavra de Deus.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "No Celeste porvir\n" +
                "Com Jesus o celeste porvir (bis)\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Pacientes podemos penar\n" +
                "Se sofrermos por nosso Jesus\n" +
                "Pois sem culpa, sem falta ou pesar\n" +
                "Viveremos no reino da luz! (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "No descanso perfeito, eternal\n" +
                "Desfrutando o labor que passou,\n" +
                "Cantaremos em tom triunfal,\n" +
                "Os louvores de quem nos amou! (Coro)\n"));

        songs.add(new Song("Nas Tormentas desta Vida", 12, "S. H. 145; T. V. 107", "I\n" +
                "\n" +
                "Nas tormentas desta vida\n" +
                "Perto está a perdição\n" +
                "Aos incautos navegantes\n" +
                "Quem trará a Salvação\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Resplandeçam nossas luzes,\n" +
                "Através do escuro mar\n" +
                "Pois nas trevas do pecado,\n" +
                "Almas podem naufragar\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Sempre brilha, em graça imensa,\n" +
                "Rico amor do eterno Deus\n" +
                "Toca a nós mostrar o rumo,\n" +
                "Na viagem para os Céus! (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nuvens de paixão mundana,\n" +
                "Obscurecem-lhes o sol\n" +
                "Ergue o grito de perigo,\n" +
                "Alça as luzes no farol! (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Aos errantes, insensatos\n" +
                "Guia ao porto divinal\n" +
                "Em Jesus há vero abrigo,\n" +
                "Do furor do temporal (Coro)\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Noite eterna se aproxima\n" +
                "Negro e denso o seu horror\n" +
                "Clama, avisa os infelizes\n" +
                "Insta-os para o salvador (Coro).\n"));

        songs.add(new Song("Avante! Avante, Ó Crentes!", 13, "S. H. 147; T. V. 283", "I\n" +
                "\n" +
                "Avante! Avante! O Crente\n" +
                "Salvados de Jesus\n" +
                "Erguei seu estandarte\n" +
                "Lutai por sua Cruz\n" +
                "Contra hostes inimigas\n" +
                "Ante essas multidões\n" +
                "O Comandante excelso,\n" +
                "Dirige os batalhões\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Avante! Avante! O Crentes,\n" +
                "Por Cristo pelejai\n" +
                "Vesti sua armadura,\n" +
                "Em seu poder marchai\n" +
                "No posto sempre achados,\n" +
                "Velando em oração\n" +
                "Por meio de perigos,\n" +
                "Seguindo o Capitão\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Avante! Avante! O Crentes,\n" +
                "Com passo triunfal\n" +
                "Hoje há combate horrendo,\n" +
                "Mui cedo a paz final!\n" +
                "Então eternamente\n" +
                "Bendito o vencedor\n" +
                "Por Deus vitoriado,\n" +
                "Com Cristo, o Salvador!\n"));

        songs.add(new Song("Careço de Jesus", 14, "S. H. 157; T. V. 144", "I\n" +
                "\n" +
                "Careço de Jesus! Sempre de ti,\n" +
                "Somente a Tua voz tem para mim valor\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "De ti, Senhor Careço, Sempre de ti\n" +
                "Oh! Dá-me a Tua bênção, aspiro a ti!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Careço de Jesus! Unido a ti Senhor\n" +
                "Pecado e tentação perdem o seu vigor! (Coro)\n" +
                "\n" +
                "III\n" +
                "Careço de Jesus! Rege meu coração\n" +
                "Ensina-me a viver em santa rectidão (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Careço de Jesus! Nas trevas e na luz\n" +
                "Sem ti a vida é vã. Sou pobre sem Jesus, (Coro)\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Careço de Jesus! Do sol dos altos\n" +
                "Céus liga-me sempre a ti, Filho do eterno Deus, (Coro)\n"));

        songs.add(new Song("Oh dia Alegre", 15, "S. H. 159", "I\n" +
                "\n" +
                "Oh! Dia alegre! Eu abracei\n" +
                "Jesus, e nele a salvação\n" +
                "O gozo deste coração,\n" +
                "Eu mais e mais publicarei\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Dia feliz dia feliz,\n" +
                "Quando em Jesus me satisfiz\n" +
                "Jesus me ensina a vigiar,\n" +
                "E confiando nele a orar\n" +
                "Dia feliz! Dia feliz\n" +
                "Quando em Jesus me satisfiz\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Completa a grande transacção\n" +
                "Jesus é meu, eu do Senhor\n" +
                "Chamou-me a voz do seu amor,\n" +
                "Cedi á imensa atracção. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Descansa ó alma! O Salvador,\n" +
                "Teu sustento, o pão dos Céus\n" +
                "E quem possui o eterno Deus,\n" +
                "Resiste a todo o tentador. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Meu sacro voto, excelso Deus,\n" +
                "De dia em dia afirmarei\n" +
                "Além da morte exultarei,\n" +
                "Teu filho e súbdito nos Céus. (Coro)\n"));

        songs.add(new Song("Sobre a Cruz J. Comprava", 16, "S. H. 168; T. V. 254", "I\n" +
                "\n" +
                "Sobre a Cruz Jesus comprava,\n" +
                "Nossos membros, todo o ser.\n" +
                "Hoje e sempre inteiramente\n" +
                "Quero a Cristo pertencer\n" +
                "Meu Senhor! Meu Senhor\n" +
                "Quero a Cristo pertencer. (*)\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Torna a minha língua a serva\n" +
                "De Jesus, meu grande Rei\n" +
                "Põe palavras nos meus lábios,\n" +
                "E teu nome exaltarei\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Oh! Dispõe o meu ouvido\n" +
                "A fechar-se a todo o mal\n" +
                "Escutando teu ensino,\n" +
                "Com respeito cordial.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Da vaidade aparta os olhos\n" +
                "Sempre atrai-os para Jesus;\n" +
                "Abre a minha fraca vista,\n" +
                "Para ver celeste luz.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Toma as mãos, para entregá-las\n" +
                "No serviço que convém\n" +
                "Diligentes para o Mestre\n" +
                "Trabalhando em todo o bem.\n" +
                "VI\n" +
                "\n" +
                "Guia os pés, no teu caminho\n" +
                "Fá-los ágeis a correr\n" +
                "Dos seus santos mandamentos\n" +
                "Nunca os deixes remover.\n" +
                "\n" +
                "VII\n" +
                "\n" +
                "Sim, Desejo pertencer-te!\n" +
                "Ouve a minha petição\n" +
                "Vem Jesus, supremo Amigo\n" +
                "Reina neste Coração.\n" +
                "\n" +
                "(*) Repete-se o último verso de cada quadra\n"));

        songs.add(new Song("Jesus Senhor me chego", 17, "S. H. 182; T. V. 160", "I\n" +
                "\n" +
                "Jesus Senhor, me chego a ti,\n" +
                "Tua ira santa mereci;\n" +
                "Se não me aceitas, ai de mim!\n" +
                "Oh! Toma-me como estou!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Oh! Toma-me como estou!\n" +
                "Sim toma-me como estou!\n" +
                "Confesso-me réu, mas Cristo morreu;\n" +
                "Oh! Toma-me como estou!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Culpado estou e sem poder;\n" +
                "Perdão tu podes conceder,\n" +
                "Morreste para socorrer,\n" +
                "Oh! Toma-me como estou. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nada de bom se acha em mim,\n" +
                "Dos meus esforços breve há fim\n" +
                "Mas salva-me Jesus, e, assim\n" +
                "Oh! Toma-me como estou. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Tu sabes por teu forte amor,\n" +
                "Mudar-me em fiel servidor\n" +
                "Oh! Serve-se de mim, Senhor,\n" +
                "E toma-me como estou. (Coro)\n"));

        songs.add(new Song("Somos Criancinhas", 18, "S. H. 197; T. V. 6", "I\n" +
                "\n" +
                "Somos criancinhas do celeste Pai?\n" +
                "Ele os pequeninos leva para onde vai\n" +
                "Com tem ura ampara nosso incerto pé\n" +
                "Aos infantes mostra paternal mercê.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Somos cordeirinhos do fiel Pastor?\n" +
                "Sempre então sigamos nosso Protector\n" +
                "Com cuidado ouçamos sua amante voz\n" +
                "Ele nos protege do leão feroz\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Somos nós soldados do Senhor Jesus?\n" +
                "Vamos. POIS, valentes, a onde nos conduz\n" +
                "Temos armadura forte, divinal\n" +
                "Com Jesus lutemos contra todo o mal.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Do Celeste remo somos cidadãos?\n" +
                "Santo Deus! Governa sobre nós, cristãos\n" +
                "Faze-nos sujeitos á tua alta lei,\n" +
                "Súbitos humildes do supremo Rei.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Fracos, acanhados, prontos, para o mal\n" +
                "Como alcançaremos sorte tão real?\n" +
                "Jesus Cristo amou-nos, auxilia os seus\n" +
                "Com poder segura nossa entrada nos Céus.\n"));

        songs.add(new Song("Divino Salvador", 19, "", "I\n" +
                "\n" +
                "Divino Salvador!\n" +
                "Contempla com favor nosso pais!\n" +
                "Dá-nos Interna paz, governo bom,\n" +
                "Dita que satisfaz, sorte feliz\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Olhamos para ti, capaz\n" +
                "Vem dominar aqui, ó Rei dos reis!\n" +
                "Dirige o pátrio Lar, ensina a governar\n" +
                "Conforme o teu mandar, por Justas leis\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ao chefe da Nação\n" +
                "Outorga a direcção do teu amor\n" +
                "Guia-o para te servir,\n" +
                "E, no eternal porvir\n" +
                "De ti gostoso ouvir doce louvor\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "A cara pátria tem\n" +
                "Sustento e todo o bem de ti, Senhor\n" +
                "Aos pobres dá comer\n" +
                "Aos ricos faz saber\n" +
                "Como convém viver em mútuo amor,\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Do crime e rebelião\n" +
                "Concede a protecção que é divinal\n" +
                "Ampara-nos!\n" +
                "Senhor! De guerras, de terror,\n" +
                "Sê nosso defensor! Desvia o mal.\n" +
                "\n" +
                "VI\n" +
                "\n" +
                "Poder supremo tens,\n" +
                "Depara os altos bens da salvação.\n" +
                "Brilhe a benigna luz que o teu favor produz;\n" +
                "Reine o Senhor Jesus sobre a nação.\n"));

        songs.add(new Song("Cristo, já Ressuscitou", 20, "S. H. 202; T. V. 87", "I\n" +
                "\n" +
                "Cristo Já ressuscitou; aleluia!\n" +
                "Sobre a morte triunfou; aleluia!\n" +
                "Tudo consumado está; aleluia!\n" +
                "Salvação de graça há aleluia.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Uma vez na Cruz sofreu; aleluia!\n" +
                "Uma vez por nós morreu; aleluia!\n" +
                "Mas agora vivo está; aleluia!\n" +
                "E para sempre reinará; aleluia!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Gratos hinos entoai; aleluia!\n" +
                "A Jesus, o grande Rei; aleluia!\n" +
                "Pois á morte o grande Rei; aleluia,\n" +
                "Pecadores para salvar, aleluia.\n"));

        songs.add(new Song("Meu Corpo, Vida e Alma", 21, "S. H. 210; T. V. 174", "I\n" +
                "\n" +
                "Meu corpo vida e alma\n" +
                "Devolvo a ti, Senhor!\n" +
                "Minha oblação inteira,\n" +
                "Segundo Teu favor.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Agora, de hoje em diante;\n" +
                "Meu amo és Tu Jesus!\n" +
                "Conduz-me em teus passos,\n" +
                "Benigna clara luz.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ó Redentor Ilustre\n" +
                "Espero em Teu poder\n" +
                "Consagro ao teu serviço,\n" +
                "Meus membros todo o ser (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Defende do pecado\n" +
                "Renova o coração\n" +
                "O faz-me puro e santo\n" +
                "Tu, sol da rectidão (Coro).\n"));

        songs.add(new Song("Tu, Cuja Voz Soou", 22, "S. H. 218; T. V. 2", "I\n" +
                "\n" +
                "Tu, cuja voz sou\n" +
                "E com poder mandou \"faça-se  a luz!\"\n" +
                "Ouve-nos com fervor e onde teu sumo amor\n" +
                "Não brilha com fulgor, faça-se a luz!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Divina luz do Céu\n" +
                "No mundo já viveu nosso Jesus\n" +
                "Cegos há claridão! Impios! Eis perdão!\n" +
                "Em todo o coração faça-se a luz!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Mestre, consolador!\n" +
                "Ânimo abrasador em nós produz\n" +
                "Paz, zelo, fé poder sempre ansiamos ter\n" +
                "Conforme o teu prazer, faça-se a luz.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Supremo sem igual!\n" +
                "Trino e uno! Imortal! Dá-nos a luz,\n" +
                "Pai! Santo é teu amor! Paciente ó Salvador!\n" +
                "Terno o Consolador! Faça-se a luz!\n"));

        songs.add(new Song("Mais Perto Quero Estar", 23, "S. H. 219; T. V. 149", "I\n" +
                "\n" +
                "Mais perto quero estar, meu Deus de ti\n" +
                "A inda que seja a dor que me une a ti\n" +
                "Sempre hei-de suplicar: mais perto quero estar.\n" +
                "\"Mais perto quero estar, Meu Deus de ti\"\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Marchando triste aqui na solidão,\n" +
                "Paz a descanso a mim teus braços dão\n" +
                "Nas trevas vou sonhar: mais perto quero estar\n" +
                "Mais perto quero estar, meu Deus de ti\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Minh’alma cantará a ti Senhor\n" +
                "E em Betel alcançará padrão de amor\n" +
                "Eu sempre hei-de rogar mais perto quero estar\n" +
                "Mais perto quero estar, meu Deus de ti.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "E quando a morte, enfim, me vier chamar\n" +
                "Nos Céus com o Senhor, irei morar\n" +
                "Então me alegrarei\n" +
                "Perto de ti, meu Rei, meu Deus, de ti!\n"));

        songs.add(new Song("Vinde Irmãos, Louvar", 24, "S. H. 220; T. V. 185", "I\n" +
                "\n" +
                "Vindo, irmãos, louvar a Deus\n" +
                "Criador da terra e Céus\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Exaltemos o Senhor!\n" +
                "Indizível seu amor!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Glória e honra ao grande Rei\n" +
                "Alta e santa é sua lei (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Obra com poder real\n" +
                "Com largueza divinal (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Dia e noite a sua mãe\n" +
                "Madurece o áureo grão (Coro)\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Com os dons da Salvação\n" +
                "Alimenta o coração (Coro)\n" +
                "\n" +
                "VI\n" +
                "\n" +
                "Vida eterna, exímia luz\n" +
                "Dele herdamos em Jesus (Coro)\n" +
                "\n" +
                "VII\n" +
                "\n" +
                "Aos perdidos, Cristo amou\n" +
                "Os culpados resgatou (Coro)\n" +
                "\n" +
                "VIII\n" +
                "\n" +
                "Celebramos a mercê\n" +
                "Que alcançou a nossa fé (Coro)\n" +
                "\n" +
                "IX\n" +
                "\n" +
                "Dá ingresso para os Céus\n" +
                "Exaltai o amor de Deus (Coro).\n"));

        songs.add(new Song("Comigo Habita", 25, "S. H. 222; T. V. 256", "I\n" +
                "\n" +
                "Comigo habita, ó Deus! A noite vem\n" +
                "As trevas crescem, Eis Senhor, convém\n" +
                "Que me socorra a tua protecção\n" +
                "Oh! Vem fazer comigo habitação!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Depressa encontrei o fim mortal\n" +
                "Desaparece o gozo terreal.\n" +
                "Mudança vejo em tudo, é corrupção;\n" +
                "Comigo faze eterna habitação!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Vem revelar-te a mim, Jesus Senhor!\n" +
                "Mestre Divino! Rei! Consolador!\n" +
                "Meu guia forte! Amparo em tentação;\n" +
                "Vem, vem fazer comigo habitação!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Presente estás nas trevas ou na luz!\n" +
                "Não há perigo andando com Jesus!\n" +
                "A morte e o túmulo não aterrarão,\n" +
                "Onde meu Deus fizer habitação.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Ó morte! Em Cristo gozo a redenção\n" +
                "Sepulcro, o pó verá ressurreição\n" +
                "No reino além não há perturbação,\n" +
                "Herdo com Deus perene habitação.\n"));

        songs.add(new Song("Redentor o Omnipotente", 26, "S. H. 242; T. V. 203", "I\n" +
                "\n" +
                "Redentor Omnipotente\n" +
                "Poderoso Salvador\n" +
                "Advogado Omnisciente\n" +
                "E Jesus meu bom Senhor.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "O amante da minha alma,\n" +
                "Tu és tudo para mim!\n" +
                "Tudo quanto eu careço,\n" +
                "Acho Jesus só em ti.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Um abrigo sempre perto,\n" +
                "Para todo o pecador,\n" +
                "Um refúgio sempre aberto\n" +
                "E Jesus, meu Salvador!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Agua viva! Pão da vida!\n" +
                "Doce sombra no calor,\n" +
                "Que ao descanso nos convida,\n" +
                "E Jesus, meu Salvador!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Sol que brilha entre as trevas\n" +
                "Com tão suave e meiga luz\n" +
                "Noite eterna dissipando\n" +
                "E meu Salvador Jesus.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "O Cordeiro imaculado\n" +
                "Que seu sangue derramou;\n" +
                "Meus pecados expiando,\n" +
                "A minha alma resgatou.\n" +
                "\n" +
                "VI\n" +
                "\n" +
                "Fundamento inabalável\n" +
                "Rocha firme e secular\n" +
                "Infalível! Imutável!\n" +
                "Quem m’o poderá tirar?\n" +
                "\n" +
                "VII\n" +
                "\n" +
                "O Caminho que, seguro,\n" +
                "Sempre para o Céu conduz;\n" +
                "Quem a Cristo pronto segue,\n" +
                "Quem tomar a sua Cruz.\n" +
                "\n" +
                "VIII\n" +
                "\n" +
                "Porta aberto, sim aberta,\n" +
                "Única da Salvação!\n" +
                "Rica fonte donde emana\n" +
                "Gozo, paz consolação.\n"));

        songs.add(new Song("Santo, Santo, Santo", 27, "S. H. 221; T. V. 304", "I\n" +
                "\n" +
                "Santo! Santo! Santo! Deus Omnipotente\n" +
                "Cedo de manhã cantaremos teu louvor;\n" +
                "Santo! Santo! Santo! Jeová triunfo!\n" +
                "És um só Deus, excelso Criador!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Santo! Santo! Santo! Todos os remidos,\n" +
                "Junto com os anjos, proclamam teu louvor:\n" +
                "Antes de formar-se o firmamento e a terra,\n" +
                "Eras e sempre és, e hás-de ser, Senhor!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Santo! Santo! Santo! Nós os pecadores\n" +
                "Não podemos ver tua glória sem temor;\n" +
                "Tu somente és Santo; não há nenhum outro\n" +
                "Perfeito em pureza, poder e amor.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Santo! Santo\" Santo! Deus Omnipotente\n" +
                "Tuas obras louvam teu nome com fervor\n" +
                "Santo! Santo! Santo! Junto e compassivo!\n" +
                "És um só Deus, supremo criador!\n"));

        songs.add(new Song("Numa Estrebaria Rude", 28, "S. H. 289; T. V. 54", "I\n" +
                "\n" +
                "Numa estrebaria rude,\n" +
                "Da cidade de Belém\n" +
                "Onde as gentes não pensavam\n" +
                "Encontrar o Sumo. Bem\n" +
                "Nela a Virgem deu a luz\n" +
                "O Menino; o Bom Jesus\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ainda que o Senhor de tudo,\n" +
                "Neste mundo veio nascer:\n" +
                "Foi seu berço a manjadoura,\n" +
                "Leite humano quis beber\n" +
                "Ele tanto se humilhou\n" +
                "No caminho que trilhou!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Para ser o bom modelo\n" +
                "Cristo honrou e obedeceu\n" +
                "A Maria, a mãe bendita,\n" +
                "E sujeito á lei cresceu;\n" +
                "Agradava em tudo a Deus\n" +
                "O seu Pai, o Rei dos Céus\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Desejamos desde agora\n" +
                "Conhecer o bom Jesus\n" +
                "Fome, sede, dor, tristezas\n" +
                "Sofreu tanto, e até á Cruz,\n" +
                "P'ra mostrar-nos compaixão,\n" +
                "E nos dar a salvação.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Quando entrarmos no paraíso\n" +
                "Lá veremos o Senhor\n" +
                "Pois o meigo e bom Menino\n" +
                "É o eterno Criador!\n" +
                "Ele, só nos abre os Céus\n" +
                "E os salva para Deus.\n"));

        songs.add(new Song("Eis dos Anjos a Harmonia", 29, "S. H. 317; T. V. 150", "I\n" +
                "Eis dos anjos a harmonia!\n" +
                "Cantam glória ao novo Rei,\n" +
                "Paz aos homens e alegria,\n" +
                "Paz com Deus e suave lei.\n" +
                "Ouçam povos exultantes,\n" +
                "Ergam Salmos triunfantes,\n" +
                "Aclamando seu Senhor\n" +
                "Nasce Cristo o Redentor.\n" +
                "Moda a terra e altos céus\n" +
                "Cantem glória ao Homem-Deus\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Cristo, eternamente honrado,\n" +
                "Do seu trono se ausentou.\n" +
                "Cristo entre homens encarnado,\n" +
                "Deus connosco nos mostrou.\n" +
                "Quão bondosa divindade!\n" +
                "Quão gloriosa humanidade!\n" +
                "Salve! Esperança de Israel\n" +
                "Luz das gentes, Emanuel\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Cante o povo resgatado\n" +
                "Gloria ao Príncipe da paz\n" +
                "Deus em Cristo revelado\n" +
                "Vida e luz ao mundo traz\n" +
                "Nasce para que renasçamos,\n" +
                "Vive para que nós vivamos.\n" +
                "Rei, profeta e Salvador!\n" +
                "Louvei todos ao Senhor!\n"));

        songs.add(new Song("Eis os Anjos a Cantar", 30, "S. H. 318; T. V. 150", "I\n" +
                "\n" +
                "Eis os anjos a cantar\n" +
                "Glória ao Menino Rei\n" +
                "Que aos homens paz vem dar\n" +
                "E a Deus a salva grei\n" +
                "Cheias de gozo, as nações\n" +
                "Venham todas proclamar\n" +
                "Que Jesus nasce em Belém,\n" +
                "E a todos quer salvar;\n" +
                "Que Ele espera-nos além,\n" +
                "No seu santo, eterno lar!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Jesus, o Menino Deus,\n" +
                "Adorar os magos vem\n" +
                "Pois ele é o Rei dos Céus,\n" +
                "Posto que nasce em Belém\n" +
                "Os cristãos! Vinde louvar\n" +
                "A Jesus o Redentor\n" +
                "Ide já anunciar\t \n" +
                "Que ele salva o pecador,\n" +
                "Que ele vem p'ra nos livrar\n" +
                "Do poder do tentador.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Eis, avante, ó cristãos\n" +
                "Vinde já vos alistar  \n" +
                "Como do Céu cidadãos,\n" +
                "Por Jesus só batalhar\n" +
                "Ele já foi para a glória\n" +
                "A mansão vos preparar!\n" +
                "Isto tende na memória-\n" +
                "Que Ele a c'roa quer dar!\n" +
                "O mal ide combater,\n" +
                "E a Cristo obedecer.\n"));

        songs.add(new Song("Que peso Ó Cristo", 31, "S. H. 328; T. V. 70", "I\n" +
                "\n" +
                "Que peso ó Cristo foi o Teu\n" +
                "Imposto sobre ti\n" +
                "A minha carga te oprimiu\n" +
                "Sofreste Tu por mim\n" +
                "Quando na Cruz, Senhor Jesus\n" +
                "Substituíste a mim.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Cálice de morte e amaridão\n" +
                "Enchido para mim foi posto ó\n" +
                "Cristo, em tua mão\n" +
                "Vazaste-o Tu por mim\n" +
                "Cálice d'horror Bebeu-o o amor\n" +
                "Bênção legou-me a mim.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "A sua vara Deus alçou\n" +
                "Feriu com ela a ti!\n" +
                "Teu Deus a ti desamparou\n" +
                "Para amparar-me a mim.\n" +
                "Teu sangue então, com expiação,\n" +
                "Verteste Tu por mim.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Da ira o temporal bramiu\n" +
                "Caindo sobre ti;\n" +
                "Pois se interpôs abrigo meu\n" +
                "Meu Fiador por mim\n" +
                "E aflito tu, Cristo Jesus,\n" +
                "Ira não há para mim.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Por mim Senhor morreste tu,\n" +
                "Em ti, pois eu morri.\n" +
                "Estás vivo, eu também.\n" +
                "Morte não há p'ra mim\n" +
                "Morto o Senhor meu Salvador\n" +
                "Deu vida eterna a mim.\n"));

        songs.add(new Song("O Som do Evangelho", 32, "S. H. 329", "I\n" +
                "\n" +
                "O som de Evangelho\n" +
                "Já se fez ouvir aqui\n" +
                "Boas novas e alegres\n" +
                "Elas são para ti e mim\n" +
                "Assim Deus nos amou.\n" +
                "Aos pobres pecadores\n" +
                "Que dos Céus seu Filho deu-nos,\n" +
                "Pra sofrer as nossas dores.\n" +
                "\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Santa paz! E perdão!\n" +
                "E o eco lá dos Céus!\n" +
                "Santa paz! E perdão!\n" +
                "Bendito nosso Deus!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "A voz do evangelho\n" +
                "Dá-nos todos a saber\n" +
                "Que fartura há para todos,\n" +
                "Sim, pra quem com fé comer:\n" +
                "\"O pão da vida sou;\n" +
                "Satisfeito ficarás;\n" +
                "Teus pecados e tua alma\n" +
                "Lavarei, e paz terás\". (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "A Voz do Evangelho\n" +
                "Ora vem-nos avisar\n" +
                "Do perigo grande e grave\n" +
                "Para quem se descuidar;\n" +
                "“Salvai-vos desde já;\n" +
                "Não vos demoreis ai,\n" +
                "Não vireis p’ra trás os olhos\n" +
                "O perigo jaz ai”. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "A voz do evangelho\n" +
                "Jubiloso som que é!\n" +
                "O amor de Jesus Cristo\n" +
                "Dá perdão mediante a fé.\n" +
                "\"As novas se vos dão\n" +
                "De haver um Salvador,\n" +
                "Poderoso e bondoso\n" +
                "Que perdoa ao pecador\". (Coro)\n"));

        songs.add(new Song("Aleluia Ressurgiu", 33, "S. H. 330", "I\n" +
                "\n" +
                "Aleluia! Ressurgiu!\n" +
                "Para o Céu Jesus já foi\n" +
                "As prisões quebrou da morte,\n" +
                "Pelos homens visto foi.\n" +
                "Ressurgiu! Ressurgiu!\n" +
                "Vive e reina lá no céu\n" +
                "Ressurgiu! Ressurgiu!  \n" +
                "Voltará ao povo seu.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Aleluia! Ressurgiu\n" +
                "Para nosso chefe ser!\n" +
                "E, morrendo, conseguiu\n" +
                "Por nós sempre interceder.\n" +
                "Ressurgiu! Ressurgiu!\n" +
                "P’ra a vitória nos ganhar.\n" +
                "Ressurgiu! Ressurgiu!\n" +
                "Para nos justificar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Aleluia! Ressurgiu!\n" +
                "A morte o ferrão tirou.\n" +
                "P'ra ressuscitar o crente,\n" +
                "A quem Ele tanto amou.\n" +
                "Ressurgiu! Ressurgiu!\n" +
                "Vive e breve voltará.\n" +
                "Ressurgiu! Ressurgiu!\n" +
                "E consigo nos terá.\n"));

        songs.add(new Song("Chuvas de Bênção", 34, "S. H. 331; T. V. 96", "I\n" +
                "\n" +
                "Chuvas de bênção teremos\n" +
                "E a promessa de Deus\n" +
                "Tempos benditos veremos\n" +
                "Chuvas de Bênçãos dos Céus\n" +
                "\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Chuvas de Bênçãos\n" +
                "Chuvas de Bênçãos dos Céus\n" +
                "Gotas benditas só temos\n" +
                "Chuvas rogamos a Deus\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Chuvas de Bênçãos teremos\n" +
                "Vida e paz. e perdão\n" +
                "Os pecadores indignos,\n" +
                "Graça dos Céus obterão (Coro)\n" +
                "\n" +
                "III\n" +
                "Chuvas de bênçãos teremos\n" +
                "Manda-nos já o Senhor\n" +
                "Dá-nos já hoje os frutos\n" +
                "Desta palavra de amor (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Chuvas de bênçãos teremos,\n" +
                "Chuvas mandadas dos Céus,\n" +
                "Bênçãos a todos os Crentes,\n" +
                "\"Bênçãos do nosso bom Deus!\" (Coro)\n"));

        songs.add(new Song("Todo aquele que ouve", 35, "S. H. 332; T. V. 236", "I\n" +
                "\n" +
                "Todo aquele que ouve, queira proclamar\n" +
                "Salvação de graça para o que aceitar\n" +
                "Possam todos este som alegre ouvir\n" +
                "Todo aquele que quer, é vir.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Todo aquele que quer! Todo aquele que quer\n" +
                "Possa todo o pródigo esta nova ouvir\n" +
                "Que seu pai Celeste o quer em casa ver!\n" +
                "Todo aquele que quer é vir.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "\"Todo aquele que quer\" não deve demorar\n" +
                "Eis a porta aberta, já podeis entrar\n" +
                "E Jesus que o pai vos quer introduzir!\n" +
                "\"Todo aquele que quer\" é vir!\"\n" +
                "\n" +
                "III\n" +
                "\n" +
                "\"Todo aquele que quer\" logo o conseguirá,\n" +
                "\"Todo aquele que quer\" por provas passará\n" +
                "\"Todo aquele que quer\" pode o Céu possuir\n" +
                "\"Todo aquele que quer é vir\" (Coro)\n"));

        songs.add(new Song("Vem a Cristo mesmo agora", 36, "S. H. 333; T. V. 51", "I\n" +
                "\n" +
                "Vem a Cristo, mesmo agora,\n" +
                "Vem assim tal qual estás\n" +
                "Que dele sem demora,\n" +
                "O perdão obterás \n" +
                "\n" +
                "II\n" +
                "\n" +
                "Crê em Cristo, sem detenção\n" +
                "Na Cruz por ti morreu;\n" +
                "Só quem tem tal crença\n" +
                "Tem entrada no Céu\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Onde emana mel e leite  \n" +
                "Te espera o seu amor; \n" +
                "Não temas que rejeite \n" +
                "Ao maior pecador.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Ele anela receber-te\n" +
                "E sua graça te dar \n" +
                "Quer consigo ver-te; \n" +
                "E contigo habitar.\n"));

        songs.add(new Song("Ainda há Lugar", 37, "S. H. 335; T. V. 103", "I\n" +
                "\n" +
                "Ainda há lugar! Á festa Nupcial\n" +
                "Vinde assistir! É festa sem rival\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Vinde, vinde, ainda há lugar, entrai\n" +
                "\n" +
                "II\n" +
                "\n" +
                "As trevas crescem, negra noite vem;\n" +
                "Chegado o sol ao seu ocaso tem (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Já cheia a sala de convivas está,\n" +
                "Mas para vós lugar ainda há. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "A porta aberta está não vos seduz\n" +
                "O brilho que derrama tanta luz? (Coro)\n" +
                "\n" +
                "V\n" +
                "\n" +
                "De amor a taça é livre, e livre aqui;\n" +
                "Tomai, bebei: O noivo vos sorri. (Coro)\n" +
                "\n" +
                "VI\n" +
                "\n" +
                "Com terna voz vos chama: vinde, entrai;\n" +
                "A festa é para vós - Gozai, gozai. (Coro)\n"));

        songs.add(new Song("Jesus Senhor me chego a Ti", 38, "S. H. 349; T. V. 160", "I\n" +
                "\n" +
                "Jesus Senhor, me chego a ti\n" +
                "Oh! Dá-me alívio mesmo aqui;\n" +
                "O teu favor estende a mim\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Aceita um pecador!\n" +
                "Eu venho como estou!\n" +
                "Eu venho como estou!\n" +
                "Porque Jesus por mim morreu,\n" +
                "Eu venho como estou!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "As minhas culpas grandes são,\n" +
                "Mas tu, que não morreste em vão,\n" +
                "Me podes conceder perdão. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Eu nada posso merecer\n" +
                "Tu vês-me prestes a morrer\n" +
                "Jesus, a ti me vou render, (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Ó! Vem agora, Salvador\n" +
                "Tu Cristo, só és meu Senhor\n" +
                "Oh! Salva-me por teu amor. (Coro)\n"));

        songs.add(new Song("Quero estar ao pé da Cruz", 39, "S. H. 353; T. V. 216", "I\n" +
                "\n" +
                "Quero estar ao pé da cruz\n" +
                "Que tão rica fonte\n" +
                "Corre franca, salutar,\n" +
                "De Sião no monte.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Sim, na cruz, sim, na cruz,\n" +
                "Na cruz me glorio,\n" +
                "\"Té que alfim vá descansar,\n" +
                "Salvo, além do rio.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "A tremer, ao pé da cruz,\n" +
                "Graça - amor achou-me;\n" +
                "Matutina estrela, ali,\n" +
                "Raios seus mandou-me. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Sempre a cruz, Filho de Deus,\n" +
                "Queiras recordar-me.\n" +
                "Dela à sombra, Salvador,\n" +
                "Queiras abrigar-me.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Junto à cruz, ardendo em fé,\n" +
                "Sem temor vigio,\n" +
                "\"Té que a terra eu possa ir ver,\n" +
                "Santa, além do rio”. (Coro)\n"));

        songs.add(new Song("Morri na Cruz por ti", 40, "S. H. 360", "I\n" +
                "\n" +
                "Morri na Cruz por ti, morri pra te livrar;\n" +
                "Meu sangue sim, verti, e posso-te salvar.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Morri, morri, na Cruz por ti,\n" +
                "Que fazes tu por mim? (Bis)\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Vivi assim por ti, com dor, com dissabor\n" +
                "Sim, tudo fiz aqui, p'ra ser teu Salvador (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Sofri na Cruz por ti a fim de ti- Salvar\n" +
                "A vida consegui, e breve ti vou dar, (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Eu trouxe a Salvação dos altos Céus louvor\n" +
                "E livre meu perdão, é grande o meu amor. (Coro)\n"));

        songs.add(new Song("Quão Bondoso Amigo", 41, "S. H. 365; T. V. 199", "I\n" +
                "\n" +
                "Quão bondoso Amigo é Cristo!\n" +
                "Carregou co’a nossa dor,\n" +
                "E nos manda que levemos\n" +
                "Os cuidados ao Senhor\n" +
                "Falta ao coração dorido,\n" +
                "Gozo, paz, consolação?\n" +
                "Isso é porque não levamos,\n" +
                "Tudo a Deus em oração.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Tu 'stás fraco e carregado\n" +
                "De cuidados e temor?\t \n" +
                "A Jesus, refúgio eterno,\n" +
                "Vai com fé, teu mal expôr.\n" +
                "Teus amigos te desprezam?\n" +
                "Conta-lhe isso em oração\n" +
                "E, com seu amor tão terno\n" +
                "Paz terás no coração\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Cristo é verdadeiro Amigo;\n" +
                "Disto prova nos mostrou,\n" +
                "Quando p'ra levar consigo,\n" +
                "Ao culpado encarnou.\n" +
                "Derramou seu sangue puro,\n" +
                "Nossa mancha p'ra lavar;\n" +
                "Gozo em vida e no futuro\n" +
                "Nele podemos alcançar.\n"));

        songs.add(new Song("Castelo Fonte", 42, "S. H. 369; T. V. 192", "I\n" +
                "\n" +
                "Castelo forte é nosso Deus,\n" +
                "Espada e bom escudo\n" +
                "Com seu poder defende os seus\n" +
                "Em todo o transe agudo\n" +
                "Com fúria pertinaz,\n" +
                "Persegue Satanás\n" +
                "Com ânimo cruel;\n" +
                "Astuto e forte é ele,\n" +
                "Igual não há na terra,\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Nossa força nada faz,\n" +
                "O homem 'stá perdido\n" +
                "Mas nosso Deus socorro traz,\n" +
                "No filho escolhido\n" +
                "Sabeis quem é Jesus?\n" +
                "O que venceu na Cruz,\n" +
                "Senhor dos altos Céus:\n" +
                "E sendo o próprio Deus\n" +
                "Triunfa na batalha.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Se nos quisessem devorar\n" +
                "Demónios não contados\n" +
                "Não nos podiam derrotar,\n" +
                "Nem ver-nos assustados\n" +
                "O príncipe do mal\n" +
                "Com rosto infernal,\n" +
                "Já condenado está\n" +
                "Vencido cairá\n" +
                "Por uma só palavra.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Que a palavra ficará\n" +
                "Sabemos com certeza\n" +
                "E nada nos assustar\n" +
                "Com Cristo por defesa\n" +
                "Se temos de perder\n" +
                "Os filhos bens, mulher,\n" +
                "Embora a vida vá\n" +
                "Por nós Jesus está\n" +
                "E dar-nos-á o seu reino.\n"));

        songs.add(new Song("Jesus Senhor e Amado", 43, "S. H. 406; T. V. 283", "I\n" +
                "\n" +
                "Jesus, Senhor amado\n" +
                "Juntos eis-nos aqui\n" +
                "Com todos os remidos\n" +
                "Um mesmo corpo, em ti\n" +
                "Espírito nos liga\n" +
                "No vínculo dc paz\n" +
                "Unindo-nos contigo\n" +
                "E gozo assim nos traz.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Que dita! Nos chegarmos\n" +
                "A ti Jesus, Senhor\n" +
                "E ter teu Santo Espírito\n" +
                "Por Administrador! \n" +
                "\tTua Palavra Santa \n" +
                "Pra nos esclarecer,\n" +
                "Tua única vontade\n" +
                "A qual obedecer\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Cercando a tua mesa\n" +
                "Que nos puseste aqui,\n" +
                "Recordação tão Santa\n" +
                "Senhor Jesus de ti\n" +
                "Da Cruz até à glória\n" +
                "Dulcíssimo é seguir\n" +
                "Os passos gloriosos\n" +
                "De quem nos quis remir\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Louvamos, adoramos\n" +
                "De unido coração,\n" +
                "E alegres entoamos\n" +
                "Com viva gratidão,\n" +
                "As tuas santas glórias,\n" +
                "Ó Cristo Salvador!\n" +
                "Cabeça que és da Igreja,\n" +
                "Manancial de amor!\n"));

        songs.add(new Song("Deus Vos Guarde", 44, "S. H. 518; T. V. 277", "I\n" +
                "\n" +
                "Deus vos guarde pelo seu poder,\n" +
                "Sempre esteja a vosso lado,\n" +
                "Vos dispense o seu cuidado\n" +
                "Deus vos guarde pelo seu poder\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Pelo seu poder e no seu amor\n" +
                "Té nos encontrarmos com Jesus;\n" +
                "Pelo seu poder. E no seu amor,\n" +
                "Oh! Que Deus vos guarde em sua luz!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Deus vos guarde bem no seu amor,\n" +
                "Consolados e contentes,\n" +
                "Achegados para os crentes;\n" +
                "Deus vos guarde bem no seu amor. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Deus vos guarde do poder do mal\n" +
                "Da ruína do pecado\n" +
                "Dos motins de qualquer lado\n" +
                "Deus vos guarde do poder do mal (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Deus vos guarde para o seu louvor\n" +
                "Para o seu presente gozo,\n" +
                "Seu serviço glorioso;\n" +
                "Deus vos guarde para o seu louvor. (Coro)\n"));

        songs.add(new Song("Quando já Livre", 45, "S. H. 527; T. V. 22", "I\n" +
                "\n" +
                "Quando já livre dos p’ridos do mar\n" +
                "Enfim á praia dourada eu chegar\n" +
                "Só ver de perto esse Deus sem par\n" +
                "Será a glória das glórias pra mim\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Será pra mim glória sem fim (Bis)\n" +
                "Ver o Senhor, assim como Ele é\n" +
                "Será a glória das glórias pra mim!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Quando por sua concessão real,\n" +
                "Eu der entrada no lar eternal,\n" +
                "Um só olhar desse Amigo leal\n" +
                "Será a glória das glórias pra mim! (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Entes queridos lá encontrarei,\n" +
                "Prazer infindo ali gozarei;\n" +
                "Mas um só meigo sorriso do rei\n" +
                "Será a Glória das glórias pra mim! (Coro)\n"));

        songs.add(new Song("Benditos Laços São", 46, "S. H. 532; T. V. 244", "I\n" +
                "\n" +
                "Benditos laços são os do fraterno amor,\n" +
                "Que assim, em santa comunhão,\n" +
                "Nos unem no Senhor!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ao mesmo trono vão as nossas petições;\n" +
                "E mútuo o gozo ou aflição\n" +
                "Dos nossos corações.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Aqui tudo é comum, o rir e o chorar.\n" +
                "Em Cristo somos todos um\n" +
                "No gozo e no lidar.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Se desta Santa União nos vamos separar,\n" +
                "No Céu eterna comunhão\n" +
                "Hemos com Deus gozar.\n"));

        songs.add(new Song("Tudo, Ó Cristo", 47, "S. H. 534; T. V. 176", "I\n" +
                "\n" +
                "Tudo, ó Cristo, a ti entrego,\n" +
                "Por ti tudo deixarei;\n" +
                "Resoluto mas submisso\n" +
                "Sempre a ti eu seguirei.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Tudo entregarei, tudo entregarei.\n" +
                "Tudo, sim, Jesus Bendito, por ti deixarei\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Tudo, ó Cristo, a ti entrego\n" +
                "Corpo e alma, eis-me aqui!\n" +
                "Todo o mundo eu renego\n" +
                "Digna-te aceitar-me a mim! (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Tudo, ó Cristo a ti entrego\n" +
                "Quero, ser somente teu!\n" +
                "Tão submisso à tua vontade\n" +
                "Como os anjos lá no Céu! (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Tudo, ó Cristo, a ti entrego\n" +
                "Oh! Eu sinto teu amor\n" +
                "Transformar a minha vida\n" +
                "E meu coração, Senhor! (Coro)\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Tudo, ó Cristo, a ti, entrego;\n" +
                "Oh! Que gozo, meu Senhor!\n" +
                "Paz perfeita, paz completa!\n" +
                "Glória. Glória ao Salvador! - S.L.G.\n"));

        songs.add(new Song("Com a Tua Mão", 48, "S. H. 547; T. V. 172", "I\n" +
                "\n" +
                "Com tua mão segura bem a minha\n" +
                "Pois eu tão frágil sou, ó Salvador\n" +
                "Que não me atrevo a dar nem um só passo\n" +
                "Sem teu amparo, meu Jesus Senhor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Com tua mão segura bem a minha\n" +
                "E mais e mais unido a ti Jesus\n" +
                "Oh! Traze-me, que nunca me desvie\n" +
                "De ti, Senhor a minha vida e luz.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Com tua mão segura bem a minha\n" +
                "Pelo Mundo alegre seguirei;\n" +
                "Mesmo onde as sombras caem mais escuras\n" +
                "Teu rosto vendo, nada temerei:\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "E, se chegar á beira desse rio,\n" +
                "Que tu por mim quiseste atravessar,\n" +
                "Com tua mão segura bem a minha\n" +
                "E sobre a morte eu hei-de triunfar.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Ou, se voltares, esses Céus rompendo,\n" +
                "Segura bem a minha mão, Senhor;\n" +
                "E, meu Jesus, oh! Leva-me contigo\n" +
                "Para onde eu goze teu eterno amor.\n"));

        songs.add(new Song("Bem de Manhã", 49, "S. H. 555; T. V. 194", "I\n" +
                "\n" +
                "Bem de manhã, embora a Céu sereno\n" +
                "Pareça um dia calmo anunciar\n" +
                "Vigia e ora, o coração pequeno\n" +
                "Um temporal pode abrigar\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Bem de manhã, e sem cessar\n" +
                "Vigiar e orar!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ao meio dia, e quando os sons da Terra\n" +
                "Abafam mais de Deus a voz d'amor\n" +
                "Recorre á oração, evita a guerra,\n" +
                "Goza paz com o Senhor (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Do dia ao fim, após os teus lidares,\n" +
                "Relembra as bênçãos do celeste amor,\n" +
                "Conta a Deus prazeres e pesares\n" +
                "Depondo em suas mãos a dor. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "E sem cessar, vigia a todo o instante\n" +
                "Que o inimigo ataca sem parar\n" +
                "Só com Jesus, em comunhão constante\n" +
                "Pode o mortal ao Céu chegar (Coro)\n"));

        songs.add(new Song("Bendito Seja o Cordeiro", 50, "S. H. 605; T. V. 213", "I\n" +
                "\n" +
                "Bendito seja o Cordeiro,\n" +
                "Que na cruz por nós padeceu!\n" +
                "Bendito seja o seu sangue,\n" +
                "Que por nós ali Ele verteu!\n" +
                "Eis nesse sangue lavados,\n" +
                "Com roupas que tão alvas são\n" +
                "Os pecadores remidos,\n" +
                "Que perante seu Deus já estão!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Alvo mais que a neve! (Bis)\n" +
                "Sim nesse sangue lavado\n" +
                "Mais alvo que a neve serei!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Quão espinhosa a coroa\n" +
                "Que Jesus por nós suportou\n" +
                "Oh quão profundas as chegas\n" +
                "Que nos provam quanto\n" +
                "Ele amou! Eis, nessas chegas purezas\n" +
                "Para o maior pecador!\n" +
                "Pois que, mais alvos que a neve\n" +
                "O teu sangue nos torna, Senhor (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Se nós a ti confessarmos,\n" +
                "E seguirmos na tua luz,\n" +
                "Tu não somente perdoas\n" +
                "Purificas também, ó Jesus\n" +
                "Sim, e de todo o pecado\n" +
                "(que maravilha de amor!)\n" +
                "Pois que mais alvos que a neve\n" +
                "O teu sangue nos torna Senhor! (Coro).\n"));

        songs.add(new Song("A Terna Voz", 51, "H. C. 8", "I\n" +
                "\n" +
                "A terna voz do Salvador\n" +
                "Com graça nos convida.\n" +
                "Chamando-nos em Seu amor\n" +
                "Querendo dar-nos vida\n" +
                "Coro\n" +
                "Nunca nos homens se ouvirá\n" +
                "Nunca nos santos Céus de luz,\n" +
                "Mais doce nota soará\n" +
                "Que o nome de Jesus.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "O Cálix de amargor\n" +
                "Jesus tem esgotado,\n" +
                "A fim de dar ao pecador\n" +
                "Perdão do seu pecado.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Por essa grande salvação\n" +
                "Dê graças todo o crente.\n" +
                "E digna de celebração\n" +
                "Agora e eternamente.\n"));

        songs.add(new Song("Ouçam a Voz", 52, "H. G. 52; T. V. 168", "I\n" +
                "\n" +
                "Ouçam a voz do Bom Pastor\n" +
                "Que no deserto, com amor,\n" +
                "Busca as ovelhas que no mal\n" +
                "Andam bem longe do curral\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Com amor, com ardor,\n" +
                "Vos convida agora o bom Pastor,\n" +
                "Com amor, com ardor,\n" +
                "Nós chamamos-vos a Cristo.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Quem ao Pastor quer ajudar\n" +
                "Essas ovelhas e buscar?\n" +
                "Quem quer guiá-las ao redil\n" +
                "Salvas do inimigo hostil?\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Outras ovelhas, ainda estão\n" +
                "Longe dc Deus, sem salvação\n" +
                "Nunca o Pastor deseja ver\n" +
                "Qualquer ovelha se perder\n"));

        songs.add(new Song("Contente Estou", 53, "H. C. 66", "I\n" +
                "\n" +
                "Contente estou porque Deus me mostrou\n" +
                "Quando aos perdidos e ingratos amou,\n" +
                "Pois que conheço, deveras, assim,\n" +
                "Que Ele clemente, cuidava de mim.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Gozo em contar o Seu grande amor,\n" +
                "Seu grande amor, Seu grande amor:\n" +
                "Gozo em contar o Seu grande amor\n" +
                "Seu grande amor por mim.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Se me perguntam como é que isto sei,\n" +
                "De que maneira tal graça ganhei?\n" +
                "Hei-de dizer que, na cruz do Senhor,\n" +
                "Deus me mostrou Seu notável amor.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nesta certeza desfruto de paz,\n" +
                "Fé no Senhor puro gozo me traz.\n" +
                "Satanás sempre vencido será:\n" +
                "Quem Deus amou, não abandonará\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Pelo seu dom foi que Deus revelou.\n" +
                "Como deverás aos homens amou;\n" +
                "Seu grande amor breve espero louvar\n" +
                "No bom descanso do seu santo lar.\n"));

        songs.add(new Song("Vem a Cristo", 54, "H. C. 88; T. V. 110", "I\n" +
                "\n" +
                "Vem a Cristo mesmo agora.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Vem, oh. Vem, pecador.\n" +
                "Vem, Vem, confiadamente\n" +
                "A Jesus o Senhor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Crendo nele, ficas salvo.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Deus não quer que tu te percas.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Cristo pode, sim, salvar-te.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "O Senhor não te rejeita.\n" +
                "\n" +
                "VI\n" +
                "\n" +
                "Ele almeja perdoar-te.\n" +
                "\n" +
                "VII\n" +
                "\n" +
                "Aleluia, Aleluia, Aleluia.\n" +
                "Ámen, Ámen, Aleluia,\n" +
                "Aleluia, Ámen.\n"));

        songs.add(new Song("Tu Deixaste Teu Trone", 55, "H. C. 70", "I\n" +
                "\n" +
                "Tu deixaste Teu trono e coroa, Jesus,\n" +
                "E quiseste para o Mundo descer.\n" +
                "Mas nem da pousada encontraste lugar\n" +
                "Quando a que Tu chegaste a nascer,\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Vem ao meu coração, Óh Cristo;\n" +
                "Tenho nele lugar para Ti.\n" +
                "Vem ao meu coração, oh Cristo, vem\n" +
                "Tenho nele lugar para Ti.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Suas honras celestes os anjos te dão\n" +
                "Em que rendem-Te, oh Cristo, louvor;\n" +
                "Mas humilde vieste a morrer, oh Jesus,\n" +
                "P'ra dar vida ao mais vil pecador.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Outra vez seus louvores os Céus te darão\n" +
                "Ao saíres glorioso daí\n" +
                "Tua voz ouviremos: oh, vinde, afinal\n" +
                "P'ra estardes bem perto de mim.\n"));

        songs.add(new Song("Uma Voz Se Eleva", 56, "H. C. 91", "I\n" +
                "\n" +
                "Uma voz se eleva do geral clamor:\n" +
                "Dai-nos luz. Dai-nos luz\n" +
                "Multidões em trevas cheias de temor\n" +
                "Pedem luz, pedem luz\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Oh.Mandai a gloriosa luz\n" +
                "De perdão, de paz e amor.\n" +
                "Oh. Mandai a preciosa luz\n" +
                "De Jesus o Salvador.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Sim por toda a parte deve reluzir\n" +
                "Essa luz, de Jesus.\n" +
                "Luz que mostra aos homens\n" +
                "Como o bem seguir:\n" +
                "Clara luz. Grata luz.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Em Jesus há vida, paz, consolação:\n" +
                "Plena luz, pura luz.\n" +
                "Seu amor divino firma o coração\n" +
                "Nessa luz, santa Luz.\n" +
                "\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "\n" +
                "Eis, pois. Oh crente, todo o Mundo achei\n" +
                "Dessa luz, de Jesus.\n" +
                "Aos milhões perdidos, sem tardar, Valei\n" +
                "Com a Luz, de Jesus.\n"));

        songs.add(new Song("Noite Feliz", 57, "T. V. 59", "I\n" +
                "\n" +
                "Noite feliz, noite de amor\n" +
                "Tudo dorme em redor\n" +
                "Só José e Maria em Belém\n" +
                "'Inda velam, sorrindo também\n" +
                "Ao Rei-Menino, ao Senhor. (Bis)\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Noite Feliz, noite de Amor \n" +
                "Na montanha ao Pastor\n" +
                "Anunciam veraz salvação\n" +
                "Pleno gozo e paz redenção\n" +
                "Por nosso bom Salvador. (Bis)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Noite Feliz, noite de amor\n" +
                "Olha, qual resplendor\n" +
                "Luz no rosto infantil de Jesus\n" +
                "Num presepe, eis do Mundo a Luz,\n" +
                "Astro do Eterno fulgor. (Bis)\n"));

        songs.add(new Song("Todos que na Terra", 58, "S. H. 17; T. V. 5", "I\n" +
                "\n" +
                "Todos que na Terra moram\n" +
                "A Deus bendigam com prazer,\n" +
                "Como os anjos o adoram,\n" +
                "Devemos nós também fazer.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Entrai na casa do Senhor,\n" +
                "Para com júbilo cantar;\n" +
                "Somos ovelhas dum Pastor,\n" +
                "A quem devemos adorar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Sejamos servos do Senhor,\n" +
                "E bem guardemos sua Lei,\n" +
                "Cantemos todo o louvor\n" +
                "Do nosso Salvador e Rei.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Tudo seu nome louvará;\n" +
                "Porque benigno é o Senhor,\n" +
                "O seu amor sem fim será;\n" +
                "E sempre o mesmo, o Benfeitor.\n"));

        songs.add(new Song("Assim Como Estou", 59, "S. H. 39; T. V. 69", "I\n" +
                "\n" +
                "Assim como estou, sem ter que dizer,\n" +
                "Senão que por mim vieste a morrer\n" +
                "E me convidaste a ti recorrer.\n" +
                "Bendito Jesus, me chego a ti (1)\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Assim como estou, sem demorar\n" +
                "Minha alma do mal querendo limpar,\n" +
                "A ti que de tudo me podes lavar,\n" +
                "Bendito Jesus, me chego a ti!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Assim como estou, em grande aflição\n" +
                "Tão digno da morte e da perdição\n" +
                "Rogando-te vida com paz e perdão\n" +
                "Bendito Jesus, me chego a ti!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Assim como estou o celeste favor\n" +
                "Me vence. Com grato e leal amor\n" +
                "Me voto a servir-te divino Senhor;\n" +
                "Bendito Jesus, me chego a ti!\n"));

        songs.add(new Song("Eu já Contente Estou", 60, "S. H. 80; T. V. 149", "I\n" +
                "\n" +
                "Eu já contente estou! Achei Jesus!\n" +
                "Cheio de alegria vou! Achei Jesus\n" +
                "Gozo que o Mundo traz\n" +
                "Mui pronto se desfaz\n" +
                "E eterna a minha paz, paz em Jesus\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Posso eu envelhecer, nunca Jesus\n" +
                "Posso eu empobrecer, rico é Jesus!\n" +
                "Tudo me suprirá\n" +
                "Sempre me velará,\n" +
                "Nada me faltará, tenho Jesus.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Quando o Mundo acabar, fica Jesus!\n" +
                "Quando o Juiz chegar, é meu Jesus!\n" +
                "Bem alegre há-de ser\n" +
                "Quando o grande Rei descer,\n" +
                "Ouvi-lo então dizer: \"Sou teu Jesus\"!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Mortalidade adeus! Vive Jesus!\n" +
                "Vou para os lindos céus ter com Jesus.\n" +
                "E minha redenção\n" +
                "E santificação\n" +
                "Justiça e perfeição tenho em Jesus.\n"));

        songs.add(new Song("Todos Juntos Levantemos", 61, "S. H. 114; T. V. 83", "I\n" +
                "\n" +
                "Todos juntos levantemos\n" +
                "Graças ao bom Salvador; \n" +
                "Grande é a sua paciência, \n" +
                "Preciso o seu amor.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Aleluia! Proclamemos seu louvor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ele o Rei divino, eterno, \n" +
                "Nos rodeia com favor, \n" +
                "Fortalece os pequeninos \n" +
                "E perdoa ao pecador. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Pois tenhamos confiança\n" +
                "Neste excelso Redentor; \n" +
                "E na glória reunidos,\n" +
                "O louvaremos melhor. (Coro)\n"));

        songs.add(new Song("Vem Filho Perdido", 62, "S. H. 132; T. V. 106", "I \n" +
                "\n" +
                "Vem filho perdido! Ó pródigo, vem!\n" +
                "Ruína te espera nas trevas além!\n" +
                "Tu, de medo gemendo! \n" +
                "Tu, de fome gemendo!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Vem filho perdido! Ó pródigo, vem! \n" +
                "Teu Pai te convida, querendo-te bem!\n" +
                "Vestes há para ornar-te!\n" +
                "Ricos dons,-vem fartar-te!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Vem, filho perdido! Oh! Volta a Jesus!\n" +
                "Bondade infinita se avista na cruz!\n" +
                "Em miséria vagando,\n" +
                "Tuas culpas chorando\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "O pródigo, escuta as vozes de amor!\n" +
                "Oh! Rompe as ciladas do vil tentador! \n" +
                "Pois em casa há bastante \n" +
                "E tu andas errante?\n"));

        songs.add(new Song("Ouça a Benigna Voz", 63, "S. H. 133", "I\n" +
                "\n" +
                "Ouça a benigna voz\n" +
                "De Cristo, o Redentor; \n" +
                "Chama-me para a salvação \n" +
                "Fruto do seu amor.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Venho meu Senhor! Venho como estou \n" +
                "Bem nenhum mereço a ti, tua voz me convidou.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Sou débil pecador,\n" +
                "Indigno e sem saber;\n" +
                "Pureza em teu sangue terei, \n" +
                "Em teu favor, poder.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nas trevas eu dormi;\n" +
                "Jesus espalha a luz! \n" +
                "E seu divino Espírito \n" +
                "A glória me conduz\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Graças por esse amor \n" +
                "Por essa redenção!\n" +
                "Tendo Jesus o salvador, \n" +
                "Eu tenho a salvação!\n"));

        songs.add(new Song("Camaradas a Divisa", 64, "S. H. 134", "I\n" +
                "\n" +
                "Camaradas! A divisa\n" +
                "Mostra-se nos Céus\n" +
                "A vitória já se avista!\n" +
                "Quem socorre é Deus!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Guarda o forte! Em breve eu venho! \n" +
                "Clama o Salvador\n" +
                "Respondamos: Venceremos \n" +
                "Pelo teu favor\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Tropas infernais, rugindo\n" +
                "Metem-nos horror; \n" +
                "Os heróis desfalecem; \n" +
                "Não há mais vigor.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nas batalhas, poderoso\n" +
                "Vem o general \n" +
                "Com bandeira flutuando \n" +
                "Sempre triunfal!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Dura e triste é a peleja!\n" +
                "Perto a Salvação \n" +
                "Viva! Viva! Camaradas, \n" +
                "Eis o Campeão!\n"));

        songs.add(new Song("É Fraca a Porta Divinal", 65, "S. H. 148; T. V. 55", "I\n" +
                "\n" +
                "E franca a porta divinal\n" +
                "Aberto a todo o mundo; \n" +
                "Por ela o pecador mortal \n" +
                "A vista amor profundo!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Oh graça imensa! Pois assim \n" +
                "A porta aberta fica a mim.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Entrai de toda a condição,\n" +
                "Graça e perdão pedindo! \n" +
                "Entrai, buscando a salvação\n" +
                "Sereis aqui bem-vindo\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Aberto! Sim! De par em par\n" +
                "Entrai com grande urgência!\n" +
                "Deus aos constantes vai mostrar \n" +
                "Real munificência.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Deposta a Cruz o vencedor,\n" +
                "Nos Céus entronizado,\n" +
                "Repousará com o Senhor \n" +
                "Seu Deus e rei amado\n"));

        songs.add(new Song("Cai a Semente no Frescor", 66, "S. H. 153; T. V. 282", "I\n" +
                "\n" +
                "Cai a semente no frescor.\n" +
                "Cai na força do calor \n" +
                "Cai na doce viração\n" +
                "Cai na triste escuridão.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Oh qual será a colheita além,\n" +
                "Á colheita além?\n" +
                "Sempre lançada, com força ou langor,\n" +
                "Com ousadia, ou com medo e tremor!\n" +
                "Já, ou nas eras do porvir,\n" +
                "Certa a colheita, a colheita tem de vir.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Sobre os rochedos tem de murchar, \n" +
                "Ou nas estradas se esperdiçar, \n" +
                "Entre os espinhos vai-se perder, \n" +
                "Ou nas campinas há-de crescer.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Há sementeira de amargor\n" +
                "Há de remorsos e negro horror \n" +
                "Há de vergonha e confusão, \n" +
                "Há de miséria e perdição.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Anda com pranto o semeador, \n" +
                "Chora os, estorvos no seu labor; \n" +
                "Ou jubiloso, com festim.\n" +
                "Nutre esperança de nobre fim.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Vale-me, grande Semeador!\n" +
                "Dá-me a semente do teu labor \n" +
                "Quero servir-te, meu Rei Jesus \n" +
                "Quero ceifar contigo em luz!\n"));

        songs.add(new Song("Hora Bendita de Oração", 67, "S. H. 155", "I\n" +
                "\n" +
                "Hora bendita de oração,\n" +
                "Que acalma o aflito coração \n" +
                "Que leva ao trono de Jesus\n" +
                "Os rogos, para auxílio e luz!\n" +
                "Em tempos de cuidado e dor\n" +
                "Me refugio em meu Senhor;\n" +
                "Salvo do engano e tentação;\n" +
                "Em folgo na hora de oração\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Hora bendita de oração\n" +
                " Quando a fervente perdição\n" +
                "Sobre ao benigno Salvador,\n" +
                "Que atende a voz do meu clamor!\n" +
                "Jesus me ordena e recorrer\n" +
                "Ao meu amor, ao seu poder; \n" +
                "Contente e sem perturbação \n" +
                "Espero a hora de oração.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Hora bendita de oração!\n" +
                "De santa paz e comunhão!\n" +
                "Desejo, enquanto aqui me achar,\n" +
                "Com fé constante, humilde orar\n" +
                "E alfim no resplendor de Deus,\n" +
                "Na glória dos mais altos Céus, \n" +
                "Me lembrarei com gratidão\n" +
                "De tão suave hora de oração.\n"));

        songs.add(new Song("Cantei e Folgai", 68, "S. H. 156", "I\n" +
                "\n" +
                "Cantai e folgai! O Messias chegou!\n" +
                "Dissiparam-se as trevas, a aurora raiou!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Dai louvores! Celebrai-o! Foi morto na cruz! \n" +
                "Dai louvores! Publicai-o, está vivo Jesus!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Cantai e folgai! Pelos impios sofreu\n" +
                "Satisfez ajustiça, seu sangue verteu!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Cantai e folgai! Temos livre perdão! \n" +
                "Jesus nos oferta real salvação!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Cantai e folgai! Nosso salvador, Deus,\n" +
                "Advoga por nós nas alturas dos Céus!\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Cantai e folgai! O Senhor voltará! \n" +
                "O Rei glorioso nas nuvens virá!\n"));

        songs.add(new Song("A Deus Supremo Benfeitor", 69, "S. H. 177; T. V. 5", "I\n" +
                "\n" +
                "A Deus supremo Benfeitor,\n" +
                "Anjos e homens dêem louvor;\n" +
                "A Deus, o Filho, a Deus, o Pai\n" +
                "E ao Espirito, gloria dai,-K\n"));

        songs.add(new Song("O Grande Amor", 70, "S. H. 205", "I\n" +
                "\n" +
                "O Grande amor do meu Jesus\n" +
                "Por mim morrendo sobre a cruz \n" +
                "Da perdição para me salvar \n" +
                "Quem poderá contar?\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Quem pode o seu amor contar? \n" +
                "Quem pode o seu amor contar? \n" +
                "O grande amor do Salvador, \n" +
                "Quem poderá contar?\n" +
                "\n" +
                "II\n" +
                "\n" +
                "O cálice que Jesus bebeu,\n" +
                "Maldição que padeceu,\n" +
                "Tudo por mim, para me salvar, \n" +
                "Quem poderá contar?\n" +
                "\n" +
                "III\n" +
                "\n" +
                "A Zombaria tão cruel,\n" +
                "Cruz sanguenta, o amargo fel, \n" +
                "Que ele sofreu para me salvar, \n" +
                "Quem poderá contar.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Incomparável Salvador\n" +
                "Quão inefável teu amor,\n" +
                "Quão impossível de sondar! \n" +
                "Imenso e sem par - H:M:W.\n"));

        songs.add(new Song("Adorno Desta Vida", 71, "S. H. 184", "I\n" +
                "\n" +
                "Qual o adorno desta vida? E o amor (bis) \n" +
                "Alegria é concedida pelo amor (bis)\n" +
                "E benigno, é paciente,\n" +
                "Não se torna maldizente. (bis) \n" +
                "Este meigo amor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Com suspeitas não se alcança doce amor (bis) \n" +
                "Onde houver desconfiança, ai do amor! (bis)\n" +
                "Pois mostremos tolerância;\n" +
                "Muitas vezes a arrogância (bis)\n" +
                "Murcha c mata o amor. \n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ainda quando for custoso, nutre amor; (bis)\n" +
                "Ao irado e furioso mostra amor (bis)\n" +
                "Não te dês por insultado, \n" +
                "Mas responde com agrado, (bis)\n" +
                "Vence pelo amor! \n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Não te irrites, mas tolera com amor (bis)\n" +
                "Tudo sofre, tudo espera pelo amor. (bis)\n" +
                "Sentimentos orgulhosos \n" +
                "Não convém aos criminosos (bis)\n" +
                "Salvos pelo amor.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Pois irmão, ao teu vizinho mostra amor; (bis) \n" +
                "O valor não é mesquinho deste amor. (bis) \n" +
                "O supremo Deus nos ama\n" +
                "Cristo para os Céus nos chama, (bis)\n" +
                "Onde reina amor!\n"));

        songs.add(new Song("Rochedo Forte", 72, "S. H. 209", "I\n" +
                "\n" +
                "Rochedo forte é o Senhor \n" +
                "Refúgio na tribulação! \n" +
                "Constante e firme amparador, \n" +
                "Refúgio na tribulação!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Oh! Cristo é nosso abrigo no temporal! \n" +
                "No temporal, no temporal!\n" +
                "Oh! Cristo é nosso abrigo no temporal! \n" +
                "Refúgio na tribulação!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Lugar de sombra no verão,\n" +
                "Descanso na tribulação\n" +
                "Vigia fiel na escuridão;\n" +
                "Descanso na tribulação.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Piloto bom no bravo amor\n" +
                "Consolo na tribulação\n" +
                "Ancoradouro singular;\n" +
                "Consolo na tribulação!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Jesus é nosso Benfeitor \n" +
                "Auxílio na tribulação!\n" +
                "Presente, eterno Salvador,\n" +
                "Auxílio na tribulação! J.G.R.\n"));

        songs.add(new Song("Salvador Bendito", 73, "S. H. 212", "I\n" +
                "\n" +
                "Salvador bendito, terno e bom Senhor \n" +
                "Só em ti confio, ó meu Salvador!\n" +
                "Sobre a cruz morreste para me livrar, \n" +
                "Tudo padeceste para me salvar.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Ó Jesus bendito, terno e bom Senhor, \n" +
                "Só em ti confio ó meu Salvador!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Só em ti confio, pois por grande amor, \n" +
                "Nunca desprezaste um só pecador. \n" +
                "Todo o que, contrito, aos teus pés chegou \n" +
                "Salvador em graça me ti alcançou.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Sim, em ti confio, salvador fiel!\n" +
                "Nunca abandonaste a teu Israel.\n" +
                "Tua excelsa graça jamais faltará! \n" +
                "O que em ti confio não perecerá.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Sempre em ti confio; grande é teu poder!\n" +
                "Todo o inimigo podes bem vencer!\n" +
                "Sim seguro e salvo leva-me, Senhor\n" +
                "Sempre protegido pelo teu amor. -H:M:W.\n"));

        songs.add(new Song("Somos Peregrinos", 74, "S. H. 217", "I\n" +
                "\n" +
                "Somos peregrinos para os lindos Céus,\n" +
                "Onde os pequeninos louvam sempre a Deus;\n" +
                "Muitos nos esperam na Sião feliz;\n" +
                "Com prazer entraram nesse bom país.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Fomos desgarrados por vil Satanás;\n" +
                "Aos extraviados Cristo outorga a paz;\n" +
                "Sem perigo vamos, confiando em Deus, \n" +
                "Pois com Cristo somos cidadãos dos Céus!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Somos pecadores salvos por Jesus;\n" +
                "Livres de terrores, vemos tua luz,\n" +
                "Guarda os cordeirinhos, nosso bom Pastor \n" +
                "Une os teus filhinhos em sincero amor.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Como os passarinhos cumprem teu querer, \n" +
                "Fazei os teus filhinhos tuas leis temer!\n" +
                "Cedo em todo o Mundo raie a salvação! \n" +
                "Cedo rompa a aurora da Ressurreição.\n"));

        songs.add(new Song("Minha Alma e Meu Corpo", 75, "S. H. 232; T. V. 174", "I\n" +
                "\n" +
                "A minha alma e meu corpo,\n" +
                "Senhor, entrego a ti, \n" +
                "Em pleno sacrifício\n" +
                "Que ofereço agora a ti.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Agora, agora mesmo,\n" +
                "Jesus, meu Salvador, \n" +
                "Eu tudo, para sempre \n" +
                "Oferto a ti, Senhor!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Meus membros, tudo, cedo\n" +
                "Aquele que tanto amou, \n" +
                "E que por sua morte \n" +
                "De tudo me salvou.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "É doce assim deixar-me\n" +
                "Na tua Santa mão \n" +
                "Ferida para alcançar-me \n" +
                "Tão plena salvação.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Sou teu, Jesus bendito!\n" +
                "Teu sangue me lavou\n" +
                "E teu divino Espirito\n" +
                "Agora me selou - H:M:W\n"));

        songs.add(new Song("Oh Quão Cedo Andei", 76, "S. H. 234", "I\n" +
                "\n" +
                "Oh! Quão cego andei, e perdido vaguei,\n" +
                "Longe, longe do meu Salvador\n" +
                "Mas do Céu Ele desceu, e seu sangue verteu \n" +
                "Pra salvar a um tão pobre pecador.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Foi na cruz, foi na cruz, onde, um dia eu vi\n" +
                "Meu pecado castigado em Jesus; \n" +
                "Foi ali pela fé que os olhos abri, \n" +
                "E agora me alegro em sua luz.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Eu ouvia falar dessa graça sem par,\n" +
                "Que do Céu trouxe nosso Jesus;\n" +
                "Ma eu surdo me fiz, converter-me não quis, \n" +
                "Aquele que por mim morreu na cruz.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Mas um dia senti meu pecado, e vi\n" +
                "Sobre mim a espada da lei; \n" +
                "Apressado fugi, em Jesus me escondi, \n" +
                "E abrigo seguro nele achei.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Quão ditoso então este meu coração.\n" +
                "Conhecendo a excelso amor,\n" +
                "Que levou meu Jesus a sofrer lá na cruz.\n" +
                "P'ra salvar a um tão pobre pecador!-H.M.W\n"));

        songs.add(new Song("Marchamos Avante", 77, "S. H. 235", "I\n" +
                "\n" +
                "Marchamos avante para a terra dos santos, \n" +
                "Onde vivem os puros já livres no mal.\n" +
                "O tu, que perdido andas longe de Deus, \n" +
                "Oh! Dize, queres ir para o Éden celestial?\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Queres ir, queres ir?\n" +
                "Oh! Dize, queres ir para o Éden celestial?\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Naquele bom País não há dor nem gemido \n" +
                "Tristezas e morte não entram ali.\n" +
                "O tu que de medo já estás consumido,\n" +
                "Oh! Dize queres ir para o Éden celestial?\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ali não há pobres, pois todos são ricos, \n" +
                "Herdeiros da vida e amor eternal;\n" +
                "Não há lá doença, não há lá enfermos.\n" +
                "Oh! Dize, queres ir para o Éden celestial?\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Descendo do Céu, Jesus nos abriu\n" +
                "Caminho direito, estrada real;\n" +
                "E voltando para o Céu, para todos deixou \n" +
                "A porta, sim aberta do Éden celestial.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Oh! Larga os prazeres tão loucos do Mundo!\n" +
                "Jesus te oferece prazer eternal.\n" +
                "Oh! Foge dos vícios, oh! Larga os pecados,\n" +
                "E dize: Quero ir para o Éden celestial\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Quero ir! Quero ir\n" +
                "Oh! Sim eu quero ir para o Éden celestial!\n"));

        songs.add(new Song("Meu Senhor, Sou Teu", 78, "S. H. 236", "I\n" +
                "\n" +
                "Meu senhor, sou teu tua, voz ouvi\n" +
                "A chamar-me com amor\n" +
                "Mas de ti mais perto eu quero estar, \n" +
                "Ó bendito Salvador!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Mais perto da tua cruz\n" +
                "Quero estar ó Salvador! \n" +
                "Mais perto da tua cruz\n" +
                "Leva-me, ó meu Senhor!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "A seguir-te só me consagro eu,\n" +
                "Constrangido pelo amor; \n" +
                "E alegre já me declaro teu, \n" +
                "Pra servir-te ati, Senhor!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Oh! Que pura e santa delícia é\n" +
                "Aos teus santos pés me achar, \n" +
                "E com viva e reverente fé, \n" +
                "Com meu Salvador falar.\n"));

        songs.add(new Song("Chegando á Cruz", 79, "S. H. 240", "I\n" +
                "\n" +
                "Chegando á cruz de meu Senhor \n" +
                "Prostrado aos pés do Redentor, \n" +
                "Ele atendeu ao meu clamor; \n" +
                "Glória ao Salvador!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Glória ao Salvador!\n" +
                "Glória ao Salvador!\n" +
                "Agora sei que Ele me salvou; \n" +
                "Gloria ao Salvador!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Que maravilha! Jesus me amou,\n" +
                "Tudo me graça me perdoou, \n" +
                "Quebrou meus laços e me livrou; \n" +
                "Glória ao Salvador! -H:M:W.\n"));

        songs.add(new Song("Eis O Estandarte", 80, "S. H. 244; T. V. 190", "I\n" +
                "\n" +
                "Eis o estandarte, tremulando á luz!\n" +
                "Eis a sua divisa: C’roa sobre cruz\n" +
                "Para a santa guerra Ele vos conduz,\n" +
                "Quem quer alistar-se sob o rei Jesus?\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Eis o estandarte, tremulando á luz: \n" +
                "Eis a sua divisa: C’roa sobre cruz!\n" +
                "\n" +
                "II\n" +
                "Guerra contra as trevas! Guerra contra o mal \n" +
                "E contra o pecado! Guerra divinal\n" +
                "Guerra contra o mundo! Nela quem entrar \n" +
                "Há de, sem reserva, tudo abandonar\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Tudo! - Soa duro? Receais a cruz?\n" +
                "Não vos envergonhe a graça de Jesus!\n" +
                "O irmãos lembrai-vos, quem poe ele sofrer, \n" +
                "A coroa, da sua mão, há-de receber!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Nesta santa guerra desejais lutar?\n" +
                "E a C'roa de glória lá no Céu ganhar?\n" +
                "A Quem quer segui-lo, eis que diz Jesus\n" +
                "“Negue-se a si mesmo, e tome a sua cruz”\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Salvador, eu hoje venho-me render; \n" +
                "Só por ti vencido poderei vencer;\n" +
                "Só contigo morto sempre viverei;\n" +
                "Tomo agora a tua cruz, meu bondoso Rei!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Sob teu estandarte marcharei, Jesus\n" +
                "Sua divisa é minha C’roa sobre cruz! - H:M:W\n"));

        songs.add(new Song("Eu Tenho Prometido", 81, "S. H. 245; T. V. 79", "I\n" +
                "\n" +
                "Eu tenho prometido\n" +
                "Seguir-te até ao fim,\n" +
                "Pois tu, Senhor, prometes,\n" +
                "Sempre guiar-me a mim; \n" +
                "Bem sei que sou mui fraco,\n" +
                "Nada posso fazer\n" +
                "Mas pela tua graça\n" +
                "Hei sempre de vencer\n" +
                "\n" +
                "II\n" +
                "\n" +
                "O mundo já venceste\n" +
                "A morte e satanás;\n" +
                "E sobre tudo reinas, \n" +
                "Ó Príncipe da Paz! \n" +
                "No Céu e cá na terra\n" +
                "É teu todo o poder,\n" +
                "E, pela tua graça,\n" +
                "Hei sempre de vencer!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Cercado de inimigos\n" +
                "Aqui no mundo estou;\n" +
                "As tentações apertam\n" +
                "Por onde quer que vou;\n" +
                "Mas tu estás mais perto,\n" +
                "Pois vens em mm viver\n" +
                "E, pela tua graça,\n" +
                "Hei sempre de vencer\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "A todos que te seguem \n" +
                "E tomam sua cruz,\n" +
                "Prometes que contigo\n" +
                "Eles hão-de estar, Jesus; \n" +
                "Descansarão pra sempre \n" +
                "Contigo ó vencedor!\n" +
                "Pois pela tua graça,\n" +
                "Venceram Salvador- H:M:W\n"));

        songs.add(new Song("Sempre, Sempre Seguirei", 82, "S. H. 248", "" +
                "COROS\tCORO I\n" +
                "\n" +
                "1 - Sempre em ti, sempre em ti confiarei\n" +
                "Oh! Meu Senhor! Tu és meu Salvador!\n" +
                "2 - Sempre ati, sempre a ti eu seguirei.\n" +
                "3 - Sempre por ti, sempre por ti pelejarei.\n" +
                "4 - Sempre em paz, sempre em paz descansarei. \n" +
                "5 - Sempre alegre, sempre alegre caminharei. H:M:W\n" +
                "\n" +
                "CORO II\n" +
                "\n" +
                "O Jesus meu salvador!\n" +
                "Quão grande é o teu amor!\n" +
                "Tu morreste sobre a cruz.\n" +
                "Por mim, ó meu Jesus! H:M:W\n" +
                "\n" +
                "CORO III\n" +
                "\n" +
                "Sempre, sempre! Seguirei a Cristo!\n" +
                "Onde quer que Ele for, eu o seguirei\n" +
                "Sempre! Sempre! Seguirei a Cristo! \n" +
                "Onde quer que Ele for, o seguirei\n"));

        songs.add(new Song("Santo, Santo, Santo Senhor", 83, "S. H. 306; T. V. 04", "I\n" +
                "\n" +
                "Santo, Santo! Santo! \n" +
                "Senhor Omnipotente! \n" +
                "Sempre o meu lábio \n" +
                "Louvores te dará.\n" +
                "Santo! Santo Santo!\n" +
                "Minh’alma reverente, \n" +
                "Deus em três pessoas \n" +
                "Bendiz e louvará.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Santo! Santo! Santo!\n" +
                "O Numeroso Coro\n" +
                "De teus escolhidos\n" +
                "Te adora sem cessar;\n" +
                "Gratos, reconhecidos,\n" +
                "As suas coroas de oiro \n" +
                "Ao redor inclinam \n" +
                "Do cristalino mar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Santo! Santo! Santo!\n" +
                "A multidão imensa\n" +
                "Dos espíritos angélicos,\n" +
                "Os quais tu estás a ver,\n" +
                "Ante ti se prostram,\n" +
                "Em tua luz banhados,\n" +
                "Ante ti, que hás sido,\n" +
                "Que és, e hás dc ser.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Santo! Santo! Santo!\n" +
                "Por mais que oculto estejas\n" +
                "Em sombras, e o homem\n" +
                "Te não possa ver,\n" +
                "Santo, serás tu só,\n" +
                "E nada há a teu lado.\n" +
                "Que iguale a caridade,\n" +
                "Que iguale o teu poder.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Santo! Santo! Santo!\n" +
                "A Glória do teu nome\n" +
                "Publicam tuas obras \n" +
                "O Céu, a terra, o mar \n" +
                "Santo! Santo! Santo!\n" +
                "Te a humanidade,\n" +
                "Ó Deus em três pessoas,\n" +
                "Ó Deus que não tens par.\n"));

        songs.add(new Song("Te Louvemos, Ó Deus", 84, "S. H. 312", "I\n" +
                "\n" +
                "Te louvemos, ó Deus peio dom de Jesus\n" +
                "Que por nós, pecadores, morreu na cruz \n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Aleluia! Toda a glória te rendemos sem fim \n" +
                "Aleluia! Tua graça Imploramos. Amem.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Te louvamos, ó Deus pelo Espírito de luz \n" +
                "Que as trevas dissipa, e a Cristo conduz.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Te louvamos, Senhor, ó Cordeiro de Deus; \n" +
                "Foste morto, mas vives eterno nos Céus.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Vem encher-nos ó Deus de celeste ardor\n" +
                "E Fazer-nos sentir tão imenso amor! J.T.H.\n"));

        songs.add(new Song("Um Pendão Real", 85, "S. H. 255", "I\n" +
                "\n" +
                "Um pendão real vos entregou o Rei\n" +
                "A vós, soldados seus;\n" +
                "Corajosos, pois, em tudo o defendei \n" +
                "Marchando para os Céus \n" +
                "Coro\n" +
                "\n" +
                "Com valor sem temor!\n" +
                "Por Cristo prontos a sofrer!\n" +
                "Bem alto erguei o seu pendão. \n" +
                "Firmes sempre até morrer!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Eis formados já os negros batalhões \n" +
                "Do grande Usurpador!\n" +
                "Declarei-vos hoje bravos campeões; \n" +
                "A vante sem temor!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Quem receio sente no seu coração,\n" +
                "Fraco se mostrar,\n" +
                "Não receberá o eterno galardão, \n" +
                "Que Cristo tem pra dar.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Pois sejamos todos a Jesus leais,\n" +
                "E a seu real pendão;\n" +
                "Os que na batalha sempre são fiéis\n" +
                "Com ele reinarão. H.M.W\n"));

        songs.add(new Song("Eis os Milhões", 86, "S. H. 258; T. V. 310", "I\n" +
                "\n" +
                "Eis os milhões que, em trevas tão medonhas, \n" +
                "Jazem perdidos sem o Salvador!\n" +
                "Quem quem irá as novas proclamando\n" +
                "Que Deus em Cristo, Salva o pecador?\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "\"Todo o poder o Pai me deu,\n" +
                "Na terra como lá no Céu!\n" +
                "Ide, pois, anunciar o Evangelho,\n" +
                "E eis-me convosco sempre!\".\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Portas abertas, eis por todo o mundo!\n" +
                "Cristãos, erguei-vos! Já avante andai!\n" +
                "Crentes em Cristo! Uni as vossas forças, \n" +
                "Da escravidão os povos libertei.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "\"Oh! Vinde a mim!”, A voz divina clama;\n" +
                "\"Vinde!\" Clamai em nome de Jesus;\n" +
                "Pra nos salvar da maldição eterna,\n" +
                "Seu sangue derramou por nós na cruz.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "O Deus! Apressa o dia glorioso,\n" +
                "Em que os remidos todos se unirão,\n" +
                "E em coro excelso, santo, jubiloso,\n" +
                "Pra todo o sempre, gloria a ti darão! - H.M.W.\n"));

        songs.add(new Song("Rocha Eterna", 87, "S. H. 274", "I\n" +
                "\n" +
                "Rocha eternal Meu Jesus \n" +
                "Como posso-me salvar?\n" +
                "Só por obras, tua luz\n" +
                "Nunca poderei ganhar; \n" +
                "Pois se me fiar na lei, \n" +
                "No inferno penarei.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Rocha eterna! Eis-me aqui!\n" +
                "Vil, perdido e infiel!\n" +
                "Para me nutrir de ti Padeceste a dor cruel!\n" +
                "Agua viva anseio ter\n" +
                "A ti sempre vou beber.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Rocha eterna, divina!\n" +
                "Quero me abrigar em ti\n" +
                "Por teu sangue tão real,\n" +
                "Que verteste já por mm, \n" +
                "Dá-me oh! Dá-me a salvação, \n" +
                "Faze-me puro o coração.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Rocha eterna! Deus de amor! \n" +
                "Nada trago nestas mãos, \n" +
                "Só abraço-te, Senhor!\n" +
                "E desprezo os meios vãos.\n" +
                "Sempre me ti esperarei,\n" +
                "E jamais perecerei! - J.G.R.\n"));

        songs.add(new Song("Bendito o Rei que vem", 88, "S. H. 320", "I\n" +
                "\n" +
                "Bendito o Rei que vem em nome do Senhor!\n" +
                "A quem esperamos! Ao qual nós amamos! \n" +
                "Bendito o Rei que vem em nome do Senhor! \n" +
                "Hosana! Hosana! Hosana nas alturas!\n" +
                "Seus gloriosos feitos entoai com fervor! \n" +
                "Todo o vale soa! Nova pra nós tão boa! \n" +
                "Bendito o Rei que vem em nome do Senhor!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Bendito o Rei que vem em nome do Senhor!\n" +
                "Tudo trazei a Ele! Vinde, ó povo dele! \n" +
                "Bendito o Rei que vem em nome do Senhor!\n" +
                "Hosana! Hosana! Hosana nas alturas!\n" +
                "Vinde, ó vinde todos a Jesus Salvador! \n" +
                "Todos com alegria! Vozes em harmonia!\n" +
                "Bendito o Rei que vem em nome do Senhor!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Bendito o Rei que vem em nome do Senhor! \n" +
                "Ó Cristo majestoso! Graças ao Deus bondoso! \n" +
                "Bendito o Rei que vem em nome do Senhor! \n" +
                "Hosana! Hosana! Hosana! Nas alturas\n" +
                "O Salvador seu povo chama a si com amor! \n" +
                "Venham os pequeninos! Graça há para meninos\n" +
                "Bendito o Rei que vem em nome do Senhor!\n"));

        songs.add(new Song("Ó Vós que Passeis", 89, "S. H. 326; T. V. 266", "I\n" +
                "\n" +
                "O vós que passais pela cruz do Calvário, \n" +
                "Podeis contemplar, sem a mínima dor,\n" +
                "Que, para livrar-nos do grande adversário, \n" +
                "Seu sangue inocente derrame o Senhor?\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Dum trono de glória celeste descendo,\n" +
                "Ele só procurou resgatar-vos; \n" +
                "Pois ei-lo em vosso lugar recebendo.\n" +
                "Da espada divina o golpe veloz.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Por vós foi Jesus, com cruel zombaria,\n" +
                "Vestido, por homens, de manto real; \n" +
                "Espinhos insulto, atroz gritaria,\n" +
                "Sem queixa sofreu do furor desleal.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Por vós em horrível suplício pregado, \n" +
                "A ira divina o seu sangue ofereceu.\n" +
                "Por vós exclamou: \" Está tudo acabado\", \n" +
                "Curvou a cabeça, e humilde morreu.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Mirai-o! Pois inda essas mãos estendidas \n" +
                "Oferecem amor e garantem perdão.\n" +
                "Trazei, pela fé, vossas almas remidas;\n" +
                "No seio de Cristo achareis salvação. - R.H.M.\n"));

        songs.add(new Song("Oh que Belos Hinos", 90, "S. H. 346", "I\n" +
                "\n" +
                "Oh que belos hinos hoje já no Céu, \n" +
                "Já do mundo o filho mau voltou! \n" +
                "Vede no caminho o Pai abraçar \n" +
                "Esse filho que ele tanto amou.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Glória! Glória! Os anjos cantam lá!\n" +
                "Glória! Glória! As harpas tocam já!\n" +
                "E o santo coro, dando glória a Deus,\n" +
                "Por mais um remido entrar nos Céus.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Oh! Que belos hinos hoje lá no Céu!\n" +
                "E que já se reconciliou\n" +
                "A rebelde alma, que rendida a Deus. \n" +
                "Renascida, para lá voltou!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "O arrependido, hoje festejai,\n" +
                "Como os anjos fazem com fervor;\n" +
                "Ide vós alegres, e anunciai\n" +
                "Que se resgatou um pecador! - M.A.M.\n"));

        songs.add(new Song("Vamos Nós Trabalhar", 91, "S. H. 352", "I\n" +
                "\n" +
                "Vamos nós trabalhar, somos servos de Deus, \n" +
                "E o mestre seguir no caminho aos Céus! \n" +
                "Com o seu bom conselho e vigor renovar \n" +
                "E fazer diligentes o que ele ordenar.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "No labor, com fervor, a servir a Jesus \n" +
                "Com esperança e fé, e com oração\n" +
                "Até que volte o Senhor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Vamos nós trabalhar, e os famintos fartar!\n" +
                "Para a fonte os sedentos com pressa levar! \n" +
                "Só na cruz do Senhor nossa glória será. \n" +
                "Pois Jesus salvação graciosa nos dá!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Vamos nós trabalhar; muito trabalho há! \n" +
                "Que o Reino das trevas desfeito será. \n" +
                "Mas o nome exaltado terá Jeová \n" +
                "Pois Jesus salvação graciosa nos dá!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Vamos nós trabalhar, ajudados por Deus, \n" +
                "Que a c'roa e vestes nos dá lá nos céus!\n" +
                "A mansão dos fiéis nossa certa será\n" +
                "Pois Jesus salvação sempiterna nos dá! - M.A.M.\n"));

        songs.add(new Song("Mais Juntos, Ó Deus", 92, "S. H. 373; T. V. 149", "I\n" +
                "\n" +
                "Mais junto, ó Deus, a ti, mais junto a ti! \n" +
                "'Inda que amarga cruz me dês aqui,\n" +
                "\tBusco meu gozo ali, (Bis)\n" +
                "Mais junto, ó Deus a ti, mais junto a ti,\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Quando ao pôr-do-sol, na solidão,\n" +
                "Durmo cansado e só meu leito o chão\n" +
                "Vejo-me cm sonho, aqui, (Bis)\n" +
                "Mais junto, ó Deus a ti, mais junto a ti!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Sejam meus passos, pois, de graus do Céu; \n" +
                "Todas as provações, proveito meu.\n" +
                "Já teu amor senti, (Bis)\n" +
                "Mais junto, ó Deus, ati, mais junto a ti\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Cheios meus dias serão de teu louvor, \n" +
                "Pedra em Betel porei, vencida a dor.\n" +
                "Põe-me, Senhor, a mim, (Bis) \n" +
                "Mais junto, ó Deus, a ti mais junto a ti! - R.H.M.\n"));

        songs.add(new Song("Além a Porta Aberta", 93, "", "I\n" +
                "\n" +
                "A cruz fulgura sempre lá,\n" +
                "Sinal de amor ardente\n" +
                "Oh! Quanto amaste, Cristo, assim! \n" +
                "Que te entregaste tu por mim!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Por mim! Por mim!\n" +
                "E quero entrar por ti.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Aquele que busca salvação\n" +
                "Jesus concede entrada \n" +
                "E a alma encontra aceitação. \n" +
                "Em seu amor firmada.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Passado o rio da morte, lá,\n" +
                "Onde Jesus espera,\n" +
                "O galardão da cruz está,\n" +
                "Eterna primavera. - R.H.M\n"));

        songs.add(new Song("Ó Cristo, Pão da Vida", 94, "S. H. 405; T. V. 252", "I\n" +
                "\n" +
                "O cristo! Pão da vida,\n" +
                "Descido lá do Céu\n" +
                "Pão para as nossas almas, \n" +
                "Que o Pai celeste deu!\n" +
                "Em ti nos alegramos,\n" +
                "Gozando mesmo aqui\n" +
                "Do alento e da doçura,\n" +
                "Que achamos sempre em ti.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Da santa e eterna vida\n" +
                "Da qual tu és o Autor,\n" +
                "A força e o sustento\n" +
                "És tu também senhor.\n" +
                "Sem ti não nos assistem\n" +
                "Nem forças nem poder;\n" +
                "De ti, nosso Alimento, \n" +
                "Queremos nós viver.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ó Cristo! Pão da vida!\n" +
                "A ti louvamos nós,\n" +
                "E ao Pai também erguemos \n" +
                "A nossa alegre voz:\n" +
                "Agradecemos sempre \n" +
                "O amor que forneceu, \n" +
                "Para nosso alimento,\n" +
                "O nosso \" Pão do Céu\" - R.H.\n"));

        songs.add(new Song("Ai, Ai, Morreu o Bom Jesus", 95, "S. H. 420; T. V. 69", "I\n" +
                "\n" +
                "Ai, Ai,! Morreu o bom Jesus,\n" +
                "Meu Soberano, meu Senhor; \n" +
                "Quis ele a tudo se entregar\n" +
                "Por mim, tão pobre pecador!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Acaso assim sofreu na cruz \n" +
                "Por culpas mi que eu cometi?\n" +
                "Oh! Misericórdia sem igual!\n" +
                "Assim sofreu Jesus por mim!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Bem fez o sol em ocultar\n" +
                "Nas trevas o seu esplendor, \n" +
                "Quando por mãos cruéis morreu \n" +
                "Jesus, do mundo o Redentor!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Oh! Vai, minha alma, lamentar\n" +
                "Tua parte nessa maldição; \n" +
                "Os teus pecados vai chorar, \n" +
                "E desfazer-te cm gratidão.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Mas nem suspiros nem teus ais\n" +
                "O mal teu podem expiar;\n" +
                "Só em Jesus há remissão\n" +
                "Para quem nele confiar. J.T.H.\n"));

        songs.add(new Song("Ao Trabalho Obreiros", 96, "S. H. 428; T. V. 252", "I\n" +
                "\n" +
                "Ao trabalho, obreiros! Já desponta o sol!\n" +
                "Ao trabalho, obreiros, d’alva ao arrebol!\n" +
                "Ao trabalho, obreiros, ante o anoitecer!\n" +
                "Ao trabalho, obreiros! O sol vai descer!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ao trabalho, obreiros! Eis o campo cm flor! \n" +
                "Ide á messe urgente do vosso labor!\n" +
                "Ao trabalho, obreiros! Sim, perseverai! \n" +
                "Há depois descanso; vinde, trabalhai!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ao trabalho, obreiros! Logo a tarde vem; \n" +
                "Horas que 'nda tendes, se aproveitem bem! \n" +
                "Ao trabalho, obreiros! Ide, trabalhai; \n" +
                "Eis o sol no ocaso, esconder-se vai!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Ao trabalho, obreiros! Já vos falta a luz\n" +
                "Já do sol os ralos se espargem a flux?\n" +
                "Ao trabalho, obreiros! Noite vai chegar; \n" +
                "Logo vem a hora de irdes repousar.\n"));

        songs.add(new Song("Oh que Belos Hinos", 97, "S. H. 444", "I\n" +
                "\n" +
                "A semana já passou \n" +
                "O Senhor guiou-nos bem \n" +
                "O seu povo se lembrou.\n" +
                "Que reunida bênção tem.\n" +
                "E dos sete o dia melhor,\n" +
                "De descanso e de louvor. (Bis)\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Vimos-te pedir perdão,\n" +
                "Dom do amado Redentor;\n" +
                "Mostra a tua compaixão,\n" +
                "Tira a nossa culpa e dor;\n" +
                "Livres de cuidado aqui,\n" +
                "Descansemos hoje em ti. (Bis)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Desejamos-te louvar,\n" +
                "Tua presença aqui sentir,\n" +
                "Neste culto encontrar\n" +
                "Esperanças do porvir,\n" +
                "Glória típica dos Céus,\n" +
                "Manifesta aqui, ó Deus. (Bis)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "\"O Evangelho tem poder\n" +
                "Para o crente consolar, \n" +
                "Para o pecador vencer \n" +
                "E de todo o mal livrar\".\n" +
                "Seja a pregação assim\n" +
                "Hoje, e sempre até ao fim. (Bis) - G.B.N.\n"));

        songs.add(new Song("Há um Fonte Carmesim", 98, "S. H. 460; T. V. 55", "I\n" +
                "\n" +
                "Há uma fonte carmesim\n" +
                "Que meu Jesus abriu,\n" +
                "Quando morreu na cruz por mim \n" +
                "E minha alma remiu.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Eu creio, sim, eu creio\n" +
                "Que ele por mim morreu\n" +
                "Que sobre a cruz, pra me salvar, \n" +
                "Tudo Jesus sofreu.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Na cruz o meu Jesus expiou\n" +
                "O mal que cometi, \n" +
                "E pela morte que penou, \n" +
                "A glória eu consegui.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "E desde que me fez co’amor\n" +
                "Andar no trilho seu,\n" +
                "Nele confio com fervor,\n" +
                "Pois que por mm morreu.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Por tua morte sobre a cruz, \n" +
                "Em glória celestial,\n" +
                "Contigo ali, ó meu Jesus,\n" +
                "Eu serei imortal. - M.G.L.A\n"));

        songs.add(new Song("Mão ao Trabalho, Jovens", 99, "S. H. 462; T. V. 252", "I\n" +
                "\n" +
                "Mãos ao trabalho, jovens!\n" +
                "Vai já passando o alvor; \n" +
                "Eia! Enquanto temos \n" +
                "Nossa vida em flor.\n" +
                "Vamos, enquanto é dia,\n" +
                "Com Força trabalhar; \n" +
                "Eia! Que, em vindo a noite, \n" +
                "Não há mais lidar.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Mãos ao trabalho, homens!\n" +
                "Andai enquanto há luz! \n" +
                "Eia! Que é tempo agora \n" +
                "De servir Jesus.\n" +
                "Ide o vigor da vida\n" +
                "Todos ao bem votar; \n" +
                "Eia! Que, em vindo a noite, \n" +
                "Não há mais lidar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Mãos ao trabalho, avante! \n" +
                "Breve nos chega o fim; \n" +
                "Eia! Enquanto a morte \n" +
                "Não toca o clarim.\n" +
                "Vamos, irmãos, á obra!\n" +
                "Por Cristo trabalhar, \n" +
                "Eia! Que, em vindo a noite, \n" +
                "Vamos descansar. - A.H.S\n"));

        songs.add(new Song("Junto ao Trono de Deus", 100, "S. H. 473; T. V. 101", "I\n" +
                "\n" +
                "Junto ao trono de Deus preparado, \n" +
                "Há Cristão, um lugar para ti;\n" +
                "Há perfumes, há gozo exaltado,\n" +
                "Há delícias profusas ali; sim! Ali; \n" +
                "De seus anjos fiéis rodeado, \n" +
                "Numa esfera de glória e de luz, \n" +
                "Junto a Deus nos espera Jesus.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Os encantos da terra não podem\n" +
                "Dar ideia do gozo dali,\n" +
                "Se na terra os prazeres acodem,\n" +
                "São prazeres que findam aqui; mas ali\n" +
                "As venturas eternas concorrem \n" +
                "Co’a existência perpétua da luz,\n" +
                "A tornar-te feliz com Jesus\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Conservemos em nossa lembrança\n" +
                "As riquezas do lindo país,\n" +
                "E guardemos connosco a esperança\n" +
                "Duma vida melhor, mais feliz; pois dali\n" +
                "Uma voz verdadeira não cansa \n" +
                "De oferecer-nos do reino da luz \n" +
                "O amor protector de Jesus.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Se quisermos gozar da ventura\n" +
                "Que no belo país haverá,\n" +
                "Somente pedir de alma pura,\n" +
                "Que de graça Jesus nos dará; pois dali,\n" +
                "Todo cheio de amor, de ternura,\n" +
                "Desse amor que mostrou lá na cruz,\n" +
                "Nos escuta, nos ouve Jesus. - Vieira.\n"));

        songs.add(new Song("Pela Fé Avistamos Além", 101, "S. H. 223", "I\n" +
                "\n" +
                "Pela fé avistamos além\n" +
                "Uma terra que brilha em fulgor! \n" +
                "Nas moradas de Jerusalém\n" +
                "Um lugar nos prepara o Senhor!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Sim, no doce porvir vivemos no lindo país\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Cantaremos no belo país.\n" +
                "Melodias de santo ardor;\n" +
                "Nessa terra celeste e feliz\n" +
                "Não há pranto, gemido, nem dor.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Sim, daremos ao nosso Jesus\n" +
                "Um tributo de grato louvor\n" +
                "Pelas bênçãos do reino de luz,\n" +
                "Pelo dom do seu rico amor. J.B.\n"));

        songs.add(new Song("Oh, Pensei Nesse Lar", 102, "S. H. 485; T. V. 222", "I\n" +
                "\n" +
                "Oh! Pensai nesse lar lá do Céu\n" +
                "Bem ao lado do rio de luz\n" +
                "Onde os santos pra sempre já gozam \n" +
                "Da presença do nosso Jesus.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Lá do Céu! Lá do Céu!\n" +
                "Oh! Pensei nesse lar lá do Céu!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Oh! Pensei nos amigos do Céu!\n" +
                "Que a jornada já têm acabado, \n" +
                "E nos cantos que soam nos ares, \n" +
                "No palácio por Deus preparado.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Hei-de ver lá no Céu meu Jesus,\n" +
                "Face a face seu rosto mirar;\n" +
                "Longe longe, cuidados, tristezas!\n" +
                "Com Jesus vou pra sempre morar.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Cedo cedo, no Céu lá estarei,\n" +
                "Vejo o fim da jornada chegar,\n" +
                "Meu Jesus ali está-me esperando,\n" +
                "E melhor estar ali que aqui estar.- L.S.\n"));

        songs.add(new Song("A Deus Etena Criador", 103, "S. H. 500; T. V. 59", "I\n" +
                "\n" +
                "Ao Deus eterna Criador\n" +
                "Ao filho nosso Salvador\n" +
                "Ao Santo Espírito de amor.\n" +
                "Dai honra, bênção e louvor, -***\n"));

        songs.add(new Song("Neste Mundo Sozinho", 104, "S. H. 503", "I\n" +
                "\n" +
                "Neste Mundo sozinho\n" +
                "Não quero nem posso andar;\n" +
                "Pois eu sou tão fraquinho, \n" +
                "Nunca me posso guardar.\n" +
                "Mas Jesus vai comigo \n" +
                "Sempre pronto a salvar;\n" +
                "Pois Ele mesmo promete\n" +
                "Que nunca me há-de deixar.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Nunca me deixar! \n" +
                "Nunca me deixar!\n" +
                "Sim, ele mesmo promete \n" +
                "Nunca me deixar!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Inimigos mui fortes\n" +
                "Procuram minha alma perder; \n" +
                "Se sozinho andasse \n" +
                "Que poderia fazer?\n" +
                "Com Jesus ao meu lado\n" +
                "Posso alegre andar,\n" +
                "Pois Ele mesmo promete\n" +
                "Que nunca me há-de deixar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nas tristezas da vida,\n" +
                "Nas dores e nas aflições,\n" +
                "E na lida do dia,\n" +
                "Nas provas e nas tentações,\n" +
                "Cristo sempre comigo\n" +
                "Vai para me livrar,\n" +
                "Pois Ele mesmo promete\n" +
                "Que nunca me há-de deixar. - H.M.W.\n"));

        songs.add(new Song("Luz Após Trevas", 105, "T. V. 239", "I\n" +
                "\n" +
                "Luz após trevas, glória após luz, \n" +
                "Ganho após perda, c’roa após cruz;\n" +
                "Paz após luta, fruto após flor:\n" +
                "Riso após pranto, gozo após dor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Crente após impo, Justo após reu;\n" +
                "Graça após ira, vista após véu; \n" +
                "Sol após chuva, mel após sal, \n" +
                "Lar após lida, bem após mal.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Perto após longe, Cristo após \"eu\";\n" +
                "Vida após tumba, terra ante o Céu;\n" +
                "Glória, paz, vida, fé c’roa e luz,\n" +
                "Tudo isso eu tenho, crendo em Jesus! - R.C.\n"));

        songs.add(new Song("Se Dá Vida as Vagas", 106, "S. H. 529", "I\n" +
                "\n" +
                "Se da Vida as vagas procelosas são,\n" +
                "Se com desalento, julgas tudo vão,\n" +
                "Conta as muitas bênçãos, dize-as duma vez, \n" +
                "Verás com surpresa, quanto Deus já fez.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Conta as bênçãos, conta quantas são, \n" +
                "Recebidas da divina mão.\n" +
                "Vem dizê-las, todas duma vez\n" +
                "Verás surpreso, quanto Deus já fez.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Tens acaso mágoas? Triste é teu lidar?\n" +
                "É a cruz pesada, que tens de levar?\n" +
                "Conta as muitas bênçãos, não duvidarás.\n" +
                "E em canto alegre os dias passarás.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Quando vires outros com seu ouro e bens, \n" +
                "Lembra que tesouros prometidos tens; \n" +
                "Nunca os bens da terra poderão comprar. \n" +
                "A mansão celeste que vais habitar.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Seja o conflito fraco ou forte cá,\n" +
                "Não te desanimes: Deus por cima está; \n" +
                "Seu divino auxñ10, minorando o mal,\n" +
                "Te dará consolo, sempre, até final,- E.S.\n"));

        songs.add(new Song("Leva Tu Contigo Nome", 107, "S. H. 530", "I\n" +
                "\n" +
                "Leva tu contigo o nome de Jesus, o Salvador; \n" +
                "Este nome dá conforto sempre, seja onde for. \n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Nome bom, doce à fé, a esperança do porvir!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Este nome leva sempre para bem te defender; \n" +
                "Ele é a arma ao teu alcance, quando o mal te aparecer.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Oh! Que nome precioso! Gozo traz ao coração;\n" +
                "Sendo por Jesus aceito, tu possuis seu perdão\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Santo nome, adorável, tem Jesus, o amado teu; \n" +
                "\"Rei dos reis, Senhor eterno\" tu o a clamarás no Céu. B.R.D.\n"));

        songs.add(new Song("Saudai o nome de Jesus", 108, "S. H. 536; T. V. 306", "I\n" +
                "\n" +
                "Saudai o nome de Jesus!\n" +
                "Arcanjos, vos prostrai:\n" +
                "Ao filho do eterno Deus \n" +
                "Com glória coroai!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ó escolhida geração\n" +
                "Do bom, eterna Pai.\n" +
                "Ao grande Autor da Salvação \n" +
                "Com glória coroai!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ó perdoados, cujo amor\n" +
                "Bem triunfante vai,\n" +
                "Ao Deus-Varão, Conquistador \n" +
                "Com glória coroai!\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Ó raças, tribos e nações, \n" +
                "Ao Rei divino honrai!\n" +
                "A quem quebrou vossos grilhões \n" +
                "Com glória coroai. - J.H.N.\n"));

        songs.add(new Song("Enquanto, Ó Salvador", 109, "S. H. 541", "I\n" +
                "\n" +
                "Enquanto, ó Salvador, teu livro ler,\n" +
                "Meus olhos vem abrir, te quero ver; \n" +
                "Da mera letra além, a ti Senhor\n" +
                "Eu buscarei, Jesus, meu redentor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "A beira-mar, Jesus partiste o pão,\n" +
                "Satisfazendo ali a multidão;\n" +
                "Da vida o pão és tu, vem pois, assim\n" +
                "Satisfazer, Senhor a mim! A mim! - H.M.W \n"));

        songs.add(new Song("É Tempo, É Tempo", 110, "S. H. 552", "I\n" +
                "\n" +
                "E tempo, é tempo, o mestre está chamando já!\n" +
                "Marchar, marchar, confiando em meu amor!\n" +
                "Partir, partir, a salvação a proclamar,\n" +
                "Com a palavra-santa do bom Salvador!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Marchar sim, avante\n" +
                "Marchar, erguendo o pendão real! Avante!\n" +
                "Sim, avante unidos, firmes sempre a avançar. \n" +
                "Glória, glória, eis que canta a multidão!\n" +
                "Consagrando todo o vosso coração,\n" +
                "Pra a Jesus obedecer, seu querer executar, \n" +
                "Entoai louvores altos! Avançar!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "\"Queremos luz\"- é o grito das nações pagãs, \n" +
                "Que vem atravessando o imenso mar. \n" +
                "Ir já sim, levando novas de amor;\n" +
                "Sem esquecer também aqui de semear.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Desperta. Igreja! O teu poder vem exerce;\n" +
                "A todos faze Cristo conhecer\n" +
                "A tua mão estende com paciente amor \n" +
                "Esforça-te da morte eterna a os deter.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Igreja, alerta! O dia prometido vem\n" +
                "Quando aclamado o Salvador será.\n" +
                "Por toda parte o bem-amado Redentor\n" +
                "Eterna glória, honra e louvor terá. A.J.R.S. \n"));

        songs.add(new Song("Ceifeiros da Seara Santa", 111, "S. H. 554", "I\n" +
                "\n" +
                "Ceifeiros da seara santa, quão poucos, fracos sois \n" +
                "Mais forte é Cristo, vosso mestre; avante, avante, pois.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Os que esperam no Senhor, renovar-se-ão,\n" +
                "Crescerão em vigor; subirão até às alturas:\n" +
                "Correrão c sen: fadiga andarão c sem cansar, \n" +
                "Voarão, e, sem fadiga, como águias serão.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Cansados, tristes sem alento, deixai-vos de chorar.\n" +
                "Se tendes tão ingente Mestre por que desanimar?\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Jesus está connosco sempre até ao dia final. \n" +
                "Coragem, pois, irmãos. A vante na obra s em igual!\n"));

        songs.add(new Song("Depois Que Cristo Me Salvou", 112, "S. H. 561", "I\n" +
                "\n" +
                "Depois que Cristo me salvou, \n" +
                "Em Céu o mundo se tomou; \n" +
                "Até no meio do sofrer\n" +
                "E Céu a Cristo conhecer,\n" +
                "\n" +
                "Refrão\n" +
                "\n" +
                "Oh! Aleluia! Sim é Céu, é Céu fruir perdão aqui! \n" +
                "Na terra ou mar o mesmo é o Senhor é Céu ali.\n" +
                "\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Pra mm longe era outrora o Céu;\n" +
                "Mas quando Cristo me valeu,\n" +
                "Então senti meu coração\n" +
                "Entrar no Céu da retidão.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Bem pouco importa eu morar\n" +
                "Em alto monte, á beira-mar,\n" +
                "Em casa ou gruta, boa ou ruim:\n" +
                "Com Cristo ali é Céu pra mm. B.R.D.\n"));

        songs.add(new Song("Tempo Para Ser Santo", 113, "S. H. 565", "I\n" +
                "\n" +
                "Tempo pra ser santo tu deves tomar,\n" +
                "Viver com teu mestre, seu livro estudar,\n" +
                "Amar e servi-lo a seu povo valer,\n" +
                "Em tudo e por tudo sua bênção obter.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Tempo pra ser puro tu deves achar, \n" +
                "A sós com teu Mestre frequente estar; \n" +
                "Teus olhos bem fitos nele sempre ter, \n" +
                "P’la tua conduta provar seu poder.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Tempo pra ser forte tu deves buscar,\n" +
                "O mestre seguindo por onde guiar,\n" +
                "No gozo ou tristeza lhe obedecer,\n" +
                "Aos seus bens conselho sempre recorrer.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Tempo pra ser útil tu deves guardar,\n" +
                "Calmo e resignado em qualquer lugar,\n" +
                "Cheio da sua graça, repleto de amor,\n" +
                "Contente e submisso aos pés do Senhor.- S.L.G\n"));

        songs.add(new Song("Mestre O Mar Se Revolta", 114, "S. H. 568", "I\n" +
                "\n" +
                "Mestre. O mar se revolta,\n" +
                "As ondas nos dão pavor,\n" +
                "O Céu se reveste de trevas, \n" +
                "Não temos um salvador!\n" +
                "Não se te dá que morramos?\n" +
                "Podes assim dormir,\n" +
                "Se a cada momento nos vemos \n" +
                "Já prestes a submergir?\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "As ondas atendem ao meu mandar, sossegai!\n" +
                "Seja o encapelado mar.\n" +
                "Á ira dos homens, o génio do mal\n" +
                "Tais águas não podem a nau traga;\n" +
                "Que leva o Mestre do Céu e mar, \n" +
                "Pois todos ouvem o meu mandar; Sossegai! Sossegai! \n" +
                "Convosco estou para vos salvar; sossegai!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Mestre tão grande tristeza\n" +
                "Me quer hoje consumir;\n" +
                "E a dor que perturba minha alma \n" +
                "Te Implora; vem-me acudir!\n" +
                "De ondas do mal que me encobrem, \n" +
                "Quem me fará sair?\n" +
                "Eu pereço, pereço, ó Mestre; \n" +
                "Te rogo, vem-me acudir!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Mestre chegou a bonança;\n" +
                "Em paz vejo o Céu e o mar;\n" +
                "O meu coração goza calma,\n" +
                "Que não poderá findar,\n" +
                "Fica comigo, ó mestre\n" +
                "Dono da terá e Ceu,\n" +
                "E assim chegarei bem seguro \n" +
                "Ao porto, destino meu,- M.G.\n"));

        songs.add(new Song("Menso e Suave", 115, "S. H. 569", "I\n" +
                "\n" +
                "Manso e suave Jesus está chamando,\n" +
                "Chama por ti e por mim;\n" +
                "Eis que às portas espera velando, Vela por ti e por mim,\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Vem já vem já! Estás cansado; vem já,\n" +
                "Manso, suave, Jesus está chamando; \n" +
                "Chama: d pecador, vem!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Que esperamos? Jesus convidando,\n" +
                "Convida a ti e a mim\n" +
                "Porque desprezas merece que está dando, Dando a ti e a mim?\n" +
                "\n" +
                "III\n" +
                "\n" +
                "O Tempo corre, as horas se passam,\n" +
                "Passam pra ti e pra mim;\n" +
                "Morte e leitos de dor presto chamam;\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Oh! Que amor que Jesus nos tem dado, \n" +
                "Dado para ti e para mm!\n" +
                "Morreu para salvar-nos do vil pecado, Salva: a ti e a mim. - F.C.S.\n"));

        songs.add(new Song("Peregrinando", 116, "S. H. 582", "I\n" +
                "\n" +
                "Peregrinando por sobre os montes, \n" +
                "Dentro dos vales, sempre na luz!\n" +
                "Cristo promete nunca deixar-me. \n" +
                "\"Eis-me convosco\", disse Jesus.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Brilho celeste! Brilho celeste!\n" +
                "Enche a minha alma glória do Céu!\n" +
                "Aleluia! Sigo cantando,\n" +
                "Dando louvores: Cristo é meu!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Sombras á roda, sombras em cima,\n" +
                "O Salvador não hão-de ocultar;\n" +
                "Ele é a luz que nunca se apaga,\n" +
                "Junto ao seu lado sempre hei-de andar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "A luz bendita me vai cercando; \n" +
                "Passos avante para a mansão!\n" +
                "Mais e mais perto ao Mestre seguindo. \n" +
                "Dando louvores p’la salvação.- B.R.D.\n"));

        songs.add(new Song("Onde Quer Que Seja", 117, "S. H. 593", "I\n" +
                "\n" +
                "Onde quer que seja, com Jesus irei; \n" +
                "Ele é meu bendito Salvador e Rei.\n" +
                "Seja para a guerra, por Ele batalhar,\n" +
                "Ou para a campina, para semear.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Onde quer, onde quer que Deus me mandar, \n" +
                "Perto do meu Salvador eu quero andar!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Onde quer que seja, onde meu Salvador\n" +
                "Diz o coração que sente o meu amor,\n" +
                "Perto dele seguro, bem seguro vou,\n" +
                "Onde quer que seja, pois, contente estou!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Seja, pois, para onde quer que me levar, \n" +
                "Acharei com ele ali meu doce lar;\n" +
                "Onde quer que seja, sempre cantarei:\n" +
                "\"Tu, Senhor, comigo estás, não temerei\" - H.M.W.\n"));

        songs.add(new Song("Quando Lá do Céu Descendo", 118, "S. H. 599", "I\n" +
                "\n" +
                "Quando lá do Céu descendo, com os seus Jesus voltar\n" +
                "E o clarim de Deus a todos proclamar\n" +
                "A logo chega desse dia da vitória do meu Rei,\n" +
                "Eu, por sua imensa graça, lá estarei.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Quando enfim chegar o dia da vitória de meu Rei,\n" +
                "Quando enfim chegar o dia,\n" +
                "Pela graça de Jesus eu lá estarei!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Nesse dia, quando os mortos hão dc a voz de Cristo ouvir\n" +
                "E das sepulturas hão-de ressurgir,\n" +
                "Os remidos, transformados não logo aclamarão seu Rei,\n" +
                "E, por sua imensa graça, lá estarei.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Pelo mundo rejeitado foi Jesus, meu Salvador; \n" +
                "Desprezaram, insultaram meu Senhor,\n" +
                "Mas faustoso vem o dia da vitória do meu Rei, \n" +
                "E, por sua imensa graça, lá estarei.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Em mim mesmo nada tenho cm que possa confiar\n" +
                "Mas Jesus morreu na cruz pra me Salvar; \n" +
                "Tão-somente nele espero, sim c sempre esperarei\n" +
                "Pois, por sua imensa graça, lá estarei! - H.M.W.\n"));

        songs.add(new Song("Ceofeiros Somos Nós Fiéis", 119, "S. H. 603", "I\n" +
                "\n" +
                "Ceifeiros somos nós, fiéis,\n" +
                "Segando para o Rei dos reis\n" +
                "Os frutos prontos para colher, \n" +
                "Que ao redor se estão a ver. \n" +
                "Assim, ao nosso Salvador\n" +
                "Rendemos preito dc louvor-\n" +
                "Ao nosso Mestre, lá no Céu,\n" +
                "Que sobre a cruz por nós morreu.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Vamos já obedecer; \n" +
                "Vamos á colheita!\n" +
                "Para quando anoitecer, \n" +
                "Ver a obra feita.\n" +
                "Pouco tempo ainda há,\n" +
                "Breve o prazo acabará. \n" +
                "Breve, breve, acabará.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Nós respigamos por Jesus,\n" +
                "Que para os campos nos conduz. \n" +
                "Se os obreiros poucos são, \n" +
                "Ociosos ficaremos? Não!\n" +
                "Ainda há campos pra ceifar, \n" +
                "Que muito fruto devem dar.\n" +
                "Não ouves Cristo perguntar:\n" +
                "\"Quem quer por mim ir trabalhar?\"\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Horas de luz pausaram Já,\n" +
                "O dia breve acabará\n" +
                "Connosco torna o teu lugar,\n" +
                "E por Jesus vem trabalhar!\n" +
                "Ocioso, por que esperas lá'?\n" +
                "Â noite logo chegará!\n" +
                "Tu queres fruto ao Céu levar,\n" +
                "Ou só apresentar? – A.W.\n"));

        songs.add(new Song("Vem Visitar tua Igreja", 120, "S. H. 608 III; T. V. 212", "I\n" +
                "\n" +
                "Vem! Visita a tua Igreja, \n" +
                "Ó bendito Salvador!\n" +
                "Ficará c sem vigor.\n" +
                "Vivifica, vivifica,\n" +
                "Nossas almas, ó Senhor.\n"));

        songs.add(new Song("Ceia do Senhor", 121, "", "I\n" +
                "\n" +
                "Em noss’alma nós tomamos \n" +
                "O Corpo ó Jesus \n" +
                "Como oferta voluntária Que nos deste lá na Cruz.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Como pão desceste\n" +
                "Vamos a ti receber pois unidos ao teu corpo nós queremos, sim viver,\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Em figura o teu Sangue \n" +
                "Nós bebemos ó Senhor \n" +
                "Pois só nele há virtude-p'ra o pecador, (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Vem concede à tua Igreja O perdão do teu amor,\n" +
                "Para que nos dois emblema\n" +
                "Contemplemos o Senhor. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Oh, Senhor por teu Espirito \n" +
                "Eu nós vem já residir\n" +
                "P'ra teu corpo e teu sangue Nós podemos discernir (Coro)\n"));

        songs.add(new Song("Inovação e Louvor", 122, "H. C. 185", "I\n" +
                "\n" +
                "Vem tu, verbo de Deus - Fazer chegar aos Céus\n" +
                "Nossa oração - Vem sim, abençoar\n" +
                "Teu povo, e prosperar mensagem,\n" +
                "Que falar da Salvação\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Vem tu, consolador - Inspira e dá fervor\n" +
                "As orações, Espirito da Paz - Afasta Satanás \n" +
                "E plena graça traz aos corações.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Ao grande e Trino Deus - Louvem os anjos seus\n" +
                "E nós também. \n" +
                "A Deus Nosso Senhor Pai Filho e condutor, louvemos com fervor \n" +
                "P’ra sempre, Ámen!\n"));

        songs.add(new Song("Divino Espírito", 123, "47. H. E", "I\n" +
                "\n" +
                "Ó sopro divinal - Real consolador e dom \n" +
                "Celestial Revela o teu amor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Nas trevas vem brilhar - Com verdadeira luz \n" +
                "E todo o mundo encaminhar - Ao Salvador\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Qual fonte nos serás - Oh, Purificador\n" +
                "Nasceste vivas, Abrirás - Nos átrios do Senhor.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Tua obra vem cumprir - Divino Instruidor\n" +
                "E toda a Glória descobrir - Do eterno Deus dc amor\n"));

        songs.add(new Song("Conta-Me", 124, "C. H. E", "I\n" +
                "\n" +
                "Conta-me a história dc Cristo-Sim, eu desejo saber\n" +
                "Todas as suas verdades - Sem faltar uma sequer.\n" +
                "Conta dos anjos cantando - Ao nascer ele em Belém \n" +
                "Glória a Deus nas alturas Paz entre os homens também, \n" +
                "Glória a Deus nas alturas Paz entre os homens também.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Conta dos dias dc prova - Que passou em Jejum \n" +
                "Pelo demónio tentado - Só, se arrugo nenhum canta\n" +
                "Dos muitos milagres - Que ele entre o povo operou \n" +
                "Conta da Santa bondade - Que sempre Cristo mostrou \n" +
                "Conta da Santa bondade - Que sempre Cristo mostrou\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Conta da Cruz horrorosa - Onde o pregaram depois\n" +
                "Com os ladrões condenados - Ele na Cruz entre os dois\n" +
                "Conta que foi para a Glória - Conta que vem outra vez\n" +
                "Conta que posso ser Salvo Pelo que Cristo já fez \n" +
                "Conta que posso ser Salvo Pelo que Cristo já fez.\n"));

        songs.add(new Song("O Grande Amor de Deus", 125, "", "I\n" +
                "\n" +
                "A Deus bendizemos por seu grande amor seu\n" +
                "Filho Bendito por nós todos deu\n" +
                "E graça concede ao mais vil pecador\n" +
                "Abrindo-lhe a porta dc entrada no Ceu\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Exultai - Exultai - E Louvai com fervor\n" +
                "A Jesus Exaltai a Jesus Redentor \n" +
                "A Deus bendizemos, porquanto do Céu\n" +
                "Seu Filho Bendito por nós todos deu.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Oh! Graça real - Foi que Jesu\n" +
                "Morrendo, seu sangue por nós derramou\n" +
                "Herança nos céus, com os salvos cm Luz\n" +
                "Legou-nos aquele que o preço pagou. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Tal prova d'amor nos persuada confiar\n" +
                "No merecimento do Filho de Deus\n" +
                "E quem a Jesus pela Fé, se entregar\n" +
                "Vai vê-lo triunfante na Glória dos céus. (Coro)\n"));

        songs.add(new Song("Cantai", 126, "T. V. 249", "I\n" +
                "\n" +
                "O meu coração pecador\n" +
                "Perdão e descanso e encontro\n" +
                "Seguindo-os conselhos\n" +
                "D'amor do Pai que do mal me chamou\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Cantai (Cantai), Cantai (Cantai)\n" +
                "No templo do nosso Senhor (Cantai) \n" +
                "Cantai (Cantai), Cantai (Cantai) \n" +
                "Ao mundo mostrai Seu amor. \n" +
                "\n" +
                "II\n" +
                "\n" +
                "Nos astros esparsos nos céus,\n" +
                "Da lua, no brando clarão.\n" +
                "Eu leio poemas de Deus\n" +
                "Que traz aos contritos perdão (Coro) \n" +
                "\n" +
                "III\n" +
                "\n" +
                "No livro bendito encontrei\n" +
                "Palavras de amor e de luz.\n" +
                "O canto celeste escutei\n" +
                "Dos anjos saudando Jesus. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Os males do mundo deixei\n" +
                "Por isso me pus a cantar,\n" +
                "Com Deus para sempre estarei,\n" +
                "Irei com Jesus ao seu Lar. (Coro)\nI\n" +
                "\n" +
                "O meu coração pecador\n" +
                "Perdão e descanso e encontro\n" +
                "Seguindo-os conselhos\n" +
                "D'amor do Pai que do mal me chamou\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Cantai (Cantai), Cantai (Cantai)\n" +
                "No templo do nosso Senhor (Cantai) \n" +
                "Cantai (Cantai), Cantai (Cantai) \n" +
                "Ao mundo mostrai Seu amor. \n" +
                "\n" +
                "II\n" +
                "\n" +
                "Nos astros esparsos nos céus,\n" +
                "Da lua, no brando clarão.\n" +
                "Eu leio poemas de Deus\n" +
                "Que traz aos contritos perdão (Coro) \n" +
                "\n" +
                "III\n" +
                "\n" +
                "No livro bendito encontrei\n" +
                "Palavras de amor e de luz.\n" +
                "O canto celeste escutei\n" +
                "Dos anjos saudando Jesus. (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Os males do mundo deixei\n" +
                "Por isso me pus a cantar,\n" +
                "Com Deus para sempre estarei,\n" +
                "Irei com Jesus ao seu Lar. (Coro)\n"));

        songs.add(new Song("Oração de Casamento", 127, "", "I\n" +
                "\n" +
                "Bendito Salvador - Com tua aprovação\n" +
                "Conduze em doce amor\n" +
                "Teu Filhos nesta união\n" +
                "O vem aos Noivos conceder\n" +
                "A Graça que lhes é mister\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Concede-lhes andar - Unidos no Senhor,\n" +
                "Ligados no templo de Deus \n" +
                "Caminhem juntos para os céus, \n" +
                "Caminhem juntos para os céus.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Senhor, sete aprouver - Ouvir nossa oração\n" +
                "Podemos compreender - O Pai que neste união\n" +
                "Imagem temos desse amor\n" +
                "Que prende a Igreja e o Salvador.\n"));

        songs.add(new Song("Eis O Estandarte", 128, "T. H. 190", "I\n" +
                "\n" +
                "Eis o estandarte, tremulando a luz\n" +
                "Eis o seu emblema - C’roa sobre a Cruz! \n" +
                "Para a Santa guerra ele vos conduz; \n" +
                "Quem quer alistar; - Se sob o Rei Jesus? \n" +
                "Eis o Estandarte, Tremulando à luz; \n" +
                "Eis o seu emblema - C'roa sobre a Cruz.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Guerra contra as trevas! - Guerra contra o mal\n" +
                "E contra o pecado guerra divinal!\n" +
                "Guerra contra o mundo\n" +
                "Nela quem entrar\n" +
                "Há-de sem reserva tudo abandonar \n" +
                "Eis o Estandarte Tremulando à luz, \n" +
                "Eis o seu emblema C'roa sobre a Cruz \n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nesta Santa guerra desejais lutar?\n" +
                "Eis coa de glória lá no Céu ganhar!\n" +
                "A quem quer segui-lo, eis que diz Jesus: \n" +
                "Negue-se a si mesmo, e tome a sua Cruz!\n" +
                "Eis o Estandarte, Tremulando à luz.\n" +
                "Eis o seu emblema C'roa sobre a Cruz.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Salvador, agora venho-me render\n" +
                "Só por Ti vencido, poderei vencer.\n" +
                "Só contigo morto, sempre viverei\n" +
                "Tomo agora a Tua Cruz meu bondoso \n" +
                "Rei sob Teu Estandarte, marcharei, Jesus, \n" +
                "Eis o meu emblema - C’roa sobre a Cruz.\n"));

        songs.add(new Song("Sou Feliz", 129, "T. V. 233", "I\n" +
                "\n" +
                "Ou seja o caminho de gozo e de luz \n" +
                "Ou seja com trevas de horror:\n" +
                "Por Cristo já tenho aprendido a dizer: \n" +
                "Tenho Paz, doce paz no Senhor.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Tenho Paz. No Senhor\n" +
                "Tenho Paz, doce Paz no Senhor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Em provas, apertos e perseguições\n" +
                "No golpes que dá Satanás,\n" +
                "Bem calmo estarei, pois no meu coração\n" +
                "Por Jesus, eu já gozo de paz. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Agora desejo com Cristo viver\n" +
                "Com ele a correia acabar,\n" +
                "Pois sei que no meio das perturbações,\n" +
                "Com Jesus sempre em Paz posso estar. (Coro)\n"));

        songs.add(new Song("A Mensagem Divinal", 130, "H. E", "I\n" +
                "\n" +
                "A divinal mensagem - Avisa ao pecador Que Deus amou ao mundo\n" +
                "E deu-lhe um Salvador - É quem em\n" +
                "Jesus Cristo crer eterna bênção há-de ter\n" +
                "Eterna bênção há-de ter\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Pecado traz a morte - Assim Deus declarou \n" +
                "E o homem sem desculpa, perante Deus ficou, \n" +
                "Mas quem em Jesus Cristo crer, \n" +
                "Eterna bênção há-de ter.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "O Pai mandou seu Filho p'ra vida nos trazer. \n" +
                "Mas Ele não podia dar vida sem morrer\n" +
                "Agora, quem em Cristo Crer Eterna bênção há-de ter,\n" +
                "Eterna Bênção há-de ter.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Não veio Cristo ao mundo - A fim de o condenar \n" +
                "Mas quis por sua morte - Aos homens resgatar,\n" +
                "E quem em Jesus Cristo Terna bênção há-de ter\n" +
                "Eterna bênção há-de ter.\n"));

        songs.add(new Song("Ao Nosso Deus e Pai", 131, "T. L. 174; T. V. 2", "I\n" +
                "\n" +
                "Ao nosso Deus e Pai - Do coração não sai\n" +
                "Grato louvor - Ao Deus que nos amou\n" +
                "De modo que entregou\n" +
                "Seu Filho e nos salvou seja louvor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Ao Salvador Jesu - Que padeceu na Cruz, \n" +
                "Seja louvor - Seu Sangue ali verteu, \n" +
                "E a nós assim valeu\n" +
                "Aquele que Morreu seja louvor\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Pelo consolador - Que veio do Senhor \n" +
                "Suba louvor - Ao Céu que o Céu abriu.\n" +
                "A quem nos redimiu \n" +
                "Damos louvor\n"));

        songs.add(new Song("Conta a História", 132, "C. H. E", "I\n" +
                "\n" +
                "Grato é contar a história\n" +
                "Do divinal favor dc Cristo\n" +
                "E sua glória dc Cristo e seu amor\n" +
                "Eu mc alegro cm descreve-la,\n" +
                "Pois sei que gozo traz-se,\n" +
                "Nada aqui me anima \n" +
                "Jesus me satisfaz.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Grato, é contar a história, \n" +
                "Grato é contar a história\n" +
                "Grato é contar a história \n" +
                "De Cristo e seu amor \n" +
                "\n" +
                "II\n" +
                "\n" +
                "Grato é contar a história\n" +
                "De quanto Cristo fez,\n" +
                "Parece, ao repeti-la mais doce cada vez,\n" +
                "Eu mc alegro cm descreve-la, \n" +
                "Pois há quem ouviu\n" +
                "Que resgatar sem Povo\n" +
                "Jesus já conseguiu. (Coro)\n"));

        songs.add(new Song("Sabereis Falar de Tudo", 133, "", "I\n" +
                "\n" +
                "Sabeis falar dc tudo que neste mundo há \n" +
                "Mas nem sequer palavra de Deus, que tudo dá! \n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Irmãos! Irmãos, falemos do nosso Salvador! \n" +
                "Oremos ou cantemos e demos-lhe louvor!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Falamos do mau tempo do frio c do calor, \n" +
                "Oh! Quão melhor seria falar do Salvador.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Falemos da bondade do grande Salvador,\n" +
                "Da sua excelsa graça do seu imenso amor\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Da cruz também falemos onde Ele nos quis dar\n" +
                "Seu sangue tão precioso c assim nos resgatar\n"));

        songs.add(new Song("Nosso Pai Celestial", 134, "By J. C.", "I\n" +
                "\n" +
                "Nosso Pai celestial se nós todos irmãos\n" +
                "Santificado És Tu Senhor… …\n" +
                "Levantamos as nossas vozes,\n" +
                "Erguemos as mãos… …\n" +
                "Que venha o Teu Reino Senhor,\n" +
                "Que seja feita a Tua vontade\n" +
                "Assim na terra como lá nos céu \n" +
                "* E não nos deixes cair em tentação \n" +
                "* E livre-nos do mal. Ámen.\n"));

        songs.add(new Song("Halleluya", 135, "", "I\n" +
                "\n" +
                "Eis mensagem do Senhor - Aleluia,\n" +
                "Palavras do bom Deus d'amor\n" +
                "Cristo salva-o pecador - Aleluia\n" +
                "Salva-o até por meio d'um olhar\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "O-olhai (ó olhai) - Irmãos olhai (ó-olhai) \n" +
                "Sim olhai só p'ra Jesus Ele salva-o pecador\n" +
                "Aleluia salva-o até por meio d’um olhar.\n" +
                "\n" +
                "II\n" +
                " \n" +
                "Vossa dívida pagou - Aleluia\n" +
                "Jesus a satisfez na sua vida entregou\n" +
                "Aleluia para vos apresentar a Deus. (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Esta oferta a vós - Aleluia \n" +
                "Confiai pois no Redentor, Oh! \n" +
                "Olhai a Cristo, só - Aleluia\n" +
                "Convertei-vos já ao nosso Deus. (Coro) \n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Aceitai a salvação - Aleluia!\n" +
                "Segui nos passos do Senhor!\n" +
                "Publicai o seu perdão - Aleluia\n" +
                "Proclamai o grande Redentor. (Coro)\n"));

        songs.add(new Song("Ai do Senhor", 136, "By N. Thovela", "I\n" +
                "\n" +
                "Ai do meu Senhor, que sofreu amargura\n" +
                "O que merecia a mim pecador\n" +
                "Dominando assim o dragão do mundo, \n" +
                "Aceitar que sim Cristo é Rei dos reis.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Cristo Senhor - Rei sem rival\n" +
                "Oh! Cristo Senhor Salvador\n" +
                "E por seu amor - Cristo Senhor,\n" +
                "Que me senti perdido a Ti Senhor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Sinto-me tão bem por Jesus (o) Senhor \n" +
                "Sinto-me tão bem por Ti Salvador\n" +
                "Sim por Teu amor me Salvei do malvado\n" +
                "Por morrer na Cruz p'ra o bem de mim. (Coro)\n"));

        songs.add(new Song("Como Foi que me Salvei", 137, "H. C. 16; T. 1. 200", "I\n" +
                "\n" +
                "Como foi que me salvei? Pelo precioso sangue,\n" +
                "Como paz com Deus achei? \n" +
                "Sempre pelo mesmo sangue.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Oh, fonte sem igual - Que lava nosso mal\n" +
                "Paz e perdão real,\n" +
                "Vemos nesse - Mesmo sangue.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Cristo fez redenção pelo precioso sangue.\n" +
                "Podes alcançar o perdão,\n" +
                "Inda pelo mesmo sangue.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Em Jesus há Salvação pelo precioso sangue.\n" +
                "Paz e santificação \n" +
                "Linda pelo mesmo sangue.\n"));

        songs.add(new Song("Hosanas", 138, "", "I\n" +
                "\n" +
                "Hosanas hosanas ao Filho de Davi,\n" +
                "Hosanas bendito o que vem em nome do Senhor\n" +
                "Bendito o que vem em nome do Senhor (fim)\n" +
                "Hosanas nas alturas nas Alturas Hosanas nas Alturas nas Alturas.\n" +
                "E quando entrou em Jerusalém se alterou\n" +
                "Toda a Cidade dizendo.\n" +
                "Quem é este?\n" +
                "E o povo dizia . . . . . . a\n" +
                "E Jesus - E Jesus\n" +
                "O profeta de Nazaré na Galileia.\n" +
                "E Jesus. - E Jesus\n" +
                "O profeta de Nazaré na Galileia (D.C)\n"));

        songs.add(new Song("Confessai-o, Jovens", 139, "", "I\n" +
                "\n" +
                "Jovens Salvos pela Cruz \n" +
                "Vosso Redentor Jesus. \n" +
                "Elevado em santa luz - Vivo está\n" +
                "Vossa pena padeceu\n" +
                "Morte e satanás venceu\n" +
                "Mas aquele que morreu -Vivo está…!\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Confessai-o Jovens crentes vosso Salvador (Sim)\n" +
                "Recebei-o sem reserva para ser Senhor! \n" +
                "Triunfante sobre o mal, com poder celestial  \n" +
                "Hoje em glória sem igual - Vivo está \n" +
                "\n" +
                "II\n" +
                "\n" +
                "Para medianeiro ser.\n" +
                "E a seu povo assim valer.\n" +
                "Cristo, com real poder e - Vivo está!\n" +
                "Publiquemos em canção - Vivo está!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Conhecendo Seu amor\n" +
                "Trabalhemos com fervor\n" +
                "Pois que o nosso Redentor - Vivo está,\n" +
                "Publiquemos em canção\n" +
                "E com voz do coração\n" +
                "Quem nos trouxe a Salvação - Vivo está!\n"));

        songs.add(new Song("Minha Vida Consagrada", 141, "T. V. 177", "I\n" +
                "\n" +
                "Minha vida consagrada seja, inteira, A ti\n" +
                "Senhor sempre minhas mãos se movem \n" +
                "Compres-te-za e com amor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Meus momentos e meus dias sejam só \n" +
                "Em teu louvor, e meus pés velozes, \n" +
                "Correm a servir-te, ó bom Senhor.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Minha voz agora toma para teu louvor \n" +
                "Cantar; toma os lábios meus fazendo-os, \n" +
                "A mensagem proclamar.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Minha prata e ouro toma, nada quero-te \n" +
                "Esconder minha inteligência gula \n" +
                "Tão-somente em teu saber.\n" +
                "\n" +
                "V\n" +
                "\n" +
                "Que meu único desejo seja só teu\n" +
                "Nome honrar; faze que meu corpo \n" +
                "Inteiro eu te posso consagrar.\n"));

        songs.add(new Song("Cristo Me Buscou", 142, "", "I\n" +
                "\n" +
                "Na densa escuridão vagueiei,\n" +
                "Mas Cristo me buscou e sua resplendor \n" +
                "Luz as trevas dissipou.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Resplendor brilha sua luz (sua Luz)\n" +
                "Para a glória Cristo me conduz (me conduz)\n" +
                "Ele resgatou-me e não me deixará, \n" +
                "Pois a sua forte mão guardará.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Andando na divina Luz\n" +
                "Eu gozo comunhão com meu Senhor\n" +
                "E salvação que me dá protecção (Coro)\n" +
                "\n" +
                "III\n" +
                "\n" +
                "As negras nuvens podem vir\n" +
                "E a visto o foscar mas hei-de ter a vera \n" +
                "Luz p'ra sempre me guiar (Coro)\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Espero meu Salvador\n" +
                "Que se entregou e face a face ali no Céu\n" +
                "O louvou por mim sem fim (Coro)\n"));

        songs.add(new Song("Cristo Bate a Porta", 143, "T. V. 109", "I\n" +
                "\n" +
                "Ouve como a porta chama alma\n" +
                "Sem consolação é Jesus que está, \n" +
                "Querendo ocupar o teu coração.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Dá lugar a Jesus Cristo alvo a\n" +
                "Porta deste já se lhes deves a\n" +
                "Colhimento sempre em ti habitará.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Oh talvez o teu companheiro\n" +
                "Desejasses receber em lugar do bom \n" +
                "Amigo que por ti ousou morrer.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Tens tu tempo para Cristo ou a Ti \n" +
                "Convida em vão hoje é tempo de\n" +
                "Aceitares esta grande Salvação.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Breve cessará a chamada do bondoso \n" +
                "Salvador vem atende o meu convite \n" +
                "E desfruta o seu amor.\n"));

        songs.add(new Song("Achei Jesus", 144, "S. Hinos 80", "I\n" +
                "\n" +
                "Eu já contente estou - Achei Jesus\n" +
                "Cheio d'alegria vou - Achei Jesus\n" +
                "Gozo que o mundo traz - mui pronto se desfaz\n" +
                "E eterna a minha paz - paz em Jesus.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Posso eu envelhecer - Nunca Jesus\n" +
                "Posso eu empobrecer - Rico é Jesus\n" +
                "Tudo me suprirá - Sempre me valerá\n" +
                "Nada me faltará - Tendo Jesus.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Quando o mundo acabar - Fica Jesus\n" +
                "Quando o Juiz chegar - E meu Jesus \n" +
                "Bem alegre há-de ser - Quando o Grande\n" +
                "Rei descer ouvi-lo então dizer - Sou teu Jesus!\n"));

        songs.add(new Song(145, "Glória do Sião", "Bento Sitoe", "", "I\n" +
                "\n" +
                "És formosa e majestosa - Linda terra dc esplendor\n" +
                "Sempr’altiva te apresentas - Tu cidade do Senhor\n" +
                "És formada sobre Cristo - Nem o inferno te destruirá\n" +
                "Descansada em santos muros - Cristo sempre vencerá\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Santa é a obra gloriosa - Me inspira fiel vigor\n" +
                "Sempre aos filhos teus eleitos - Deus demonstrou o seu amor\n" +
                "Com graça da promessa - Que pois há-de vacilar? \n" +
                "Tenho vida, amor parene - Certo hei-de triunfar\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Povo santo e remido - Limpo está de todo o mal\n" +
                "Cristo mesmo é quem tu escolhes com a graça divinal\n" +
                "Para sempre e sobre os povos - O teu Remo governara\n" +
                "Com a glória seminterna - A Deus sempre servirá!\n"));

        songs.add(new Song("Redenção", 146, "S. H. 490", "I\n" +
                "\n" +
                "Teu pecado resgatado - Foi na Cruz por teu amor\n" +
                "E da morte triste sorte - Me livraste tu Senhor\n" +
                "\n" +
                "II\n" +
                "\n" +
                "V cm, inflama viva chama - Em meu peito, bem sem fim!\n" +
                "Que te adora, e te implora; Oh! Jesus, habita em mim!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Se hesitante, vacilante - Oiço a voz do tentador\n" +
                "Tu me guias, me auxilias - E me tomas vencedor\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Redimida, se tem vida - A minha alma em teu amor!\n" +
                "Com apreço reconheço Quanto devo a ti, Senhor.\n"));

        songs.add(new Song("Última Hora", 147, "C. H. E. 65", "I\n" +
                "\n" +
                "Ao findar o labor desta vida - Quando a morte ao teu lado chegar \n" +
                "Que destino há-de ter a tua alma - Qual será no futuro, teu lar?\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Meu amigo hoje tens tu escolhas - Vida ou morte qual vais aceitar?\n" +
                "Amanhã pode ser muito tarde - Hoje Cristo te quer libertar!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Tu procuras a paz neste mundo - Em prazeres que passam em vão\n" +
                "Mas nas últimas horas da vida - Eles nunca te satisfarão\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Meu amigo talvez tenhas rido - Ao ouvires falar de Jesus\n" +
                "Não te esqueças de que p'ra salvar-te - Ele deu sua vida na Cruz.\n"));

        songs.add(new Song("O Amor de Deus", 148, "C. H. E. 3", "I\n" +
                "\n" +
                "Contente estou porque Deus me mostrou \n" +
                "Quanto aos perdidos e ingratos amou \n" +
                "Pois que conheço deveras assim\n" +
                "Que ele clemente, cuidava de mim.\n" +
                " \n" +
                "Coro\n" +
                "\n" +
                "Gozo é contar - O seu grande amor\n" +
                "Seu grande amor - Seu grande amor \n" +
                "Gozo é contar - O seu Grande amor \n" +
                "Seu grande amor por mim.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Se me perguntam com é que isto sei\n" +
                "De que maneira tal graça ganhei \n" +
                "Hei-de dizer que na Cruz do Senhor, \n" +
                "Deus me mostrou seu notável amor.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Nesta certeza desfruto de Paz, \n" +
                "Fé no Senhor, puro gozo me traz\n" +
                "Satanás sempre vencido será\n" +
                "Quem Deus amou não abandonará.\n"));

        songs.add(new Song("Ao Meditar", 149, "T. V. 69", "I\n" +
                "\n" +
                "Ao meditar Jesus Senhor\n" +
                "Na tua amarga Cruz por mm\n" +
                "Com gratidão e com louvor\n" +
                "Celebro a quem amou-me assim\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Mas como o Salvador contar \n" +
                "O amor que foi à Cruz por mim\n" +
                "Não poderá ninguém sondar\n" +
                "A dor de quem sofreu assim\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Pecado meu Te fez sofrer\n" +
                "De Deus o desampare ali\n" +
                "Mas mesmo pelo teu amor\n" +
                "Pudeste vida dar-me em ti\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Tu que contigo vá gozar\n" +
                "O fruto dessa Cruz Senhor \n" +
                "Que aqui tu possas sempre achar \n" +
                "A mim agrado e vero amor.\n"));

        songs.add(new Song("Quer Nas Trevas, Quer na Luz", 150, "", "I\n" +
                "\n" +
                "Quer nas trevas, quer na Luz - Só a Ti recorrerei\n" +
                "Só a ti meu bom Jesus - E socorro acharei Coro:\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Só a ti meu Senhor - Só a Ti recorrerei\n" +
                "Só a ti Salvador - Um refúgio encontrarei.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Quando lutas, penas, dores - Neste mundo encontrar\n" +
                "Eu te buscarei Senhor - E viras-me consolar \n" +
                "\n" +
                "III\n" +
                "\n" +
                "Quando o grande Tentador - A minha alma combater,\n" +
                "Pela graça do Senhor Todo o mal hei-de vencer\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "Fortes lutas eu terei - Neste Mundo enganador,\n" +
                "Mas socorro eu acharei - Em ti sempre oh Senhor.\n"));

        songs.add(new Song("Louvai a Deus", 151, "", "I\n" +
                "\n" +
                "Louvai a Deus - Com júbilo cantando\n" +
                "E seu amor ao mundo anunciar\n" +
                " De Cristo as glorias - Juntos celebrando \n" +
                "Do Salvador a fama publicai \n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Os prodígios cantaremos - De Jesus o Redentor \n" +
                "Forças, vida, bens daremos - P’ra falar do seu amor\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Louvai a Deus - Senhor Omnipotente\n" +
                "A quem devemos - Nossa salvação\n" +
                "Que habita os Céus - Porém aqui presente\n" +
                "Seguros guiados seus - Por sua mão\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Louvai a Deus - E alegres, adorai-o\n" +
                "Mil graças damos - Por seu grande amor\n" +
                " Com a celeste multidão - Louvai-o\n" +
                "Deus, Sempiterno - E nosso Redentor\n"));

        songs.add(new Song("Eis-Me Convosco", 152, "", "I\n" +
                "\n" +
                "Peregrinando por sobre os montes,\n" +
                "E pelos vales, Sempre na luz \n" +
                "Cristo promete nunca deixar-me:\n" +
                "\"Eis-me convosco\", disse Jesus.\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Brilho Celeste! Brilho Celeste!\n" +
                "Enche a minha alma glória do Céu! \n" +
                "Aleluia! Sigo Cantando,\n" +
                "Dando louvores, pois Cristo é meu!\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Sombras à roda, sombras cm cima,\n" +
                "O Salvador não hão-de ocultar;\n" +
                "Ele é a luz, que nunca se apaga,\n" +
                "Bem ao seu lado, sempre hei dc andar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "A luz bendita me vai cercando\n" +
                "Em meu caminho para a Mansão; \n" +
                "Mais e mais perto seguindo o Mestre, \n" +
                "Possuo o gozo da salvação! B.R.D.\n"));

        songs.add(new Song("Pastor Divino", 153, "", "I\n" +
                "\n" +
                "Eis - nós, ó Pastor divino,\n" +
                "Todos juntos num lugar,\n" +
                "Como ovelhas, congregadas\n" +
                "Teu auxílio a suplicar\n" +
                "Sê presente (bis)\n" +
                "O rebanho o apascentar.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Guia os tristes, fatigados, \n" +
                "Ao aprisco do Senhor!\n" +
                "Leva os tenros cordeirinhos\n" +
                "Nos teus braços, bom Pastor, \n" +
                "As pastagens (bis)\n" +
                "De celeste e doce amor!\n" +
                "\n" +
                "III\n" +
                "\n" +
                "O Jesus bondoso, escuta\n" +
                "Nossa humilde petição\n" +
                "Vem encher o teu rebanho\n" +
                "DC sincera gratidão\n" +
                "Cantaremos (bis)\n" +
                "Tua imensa compaixão.\n"));

        songs.add(new Song("Noite Jubilosa", 154, "", "I\n" +
                "\n" +
                "Noite, Jubilosa\n" +
                "Noite Portentosa\n" +
                "Doce Luz do Feliz Natal\n" +
                "Deus connosco habita\n" +
                "Cristo é Emmanuel\n" +
                "Louvor ao Deus Divinal \n" +
                "\n" +
                "II\n" +
                "\n" +
                "Noite Jubilosa\n" +
                "Noite Portentosa\n" +
                "Doce Luz do feliz Natal\n" +
                "Cristo nos liberta\n" +
                "Cristo Vida certa\n" +
                "Louvor ao Deus Divinal \n" +
                "\n" +
                "III\n" +
                "\n" +
                "Noite Jubilosa\n" +
                "Noite Portentosa\n" +
                "Doce Luz do Feliz natal\n" +
                "Anjos cantam Hinos\n" +
                "Ao Rei Pequenino\n" +
                "Louvor ao Deus Divinal\n"));

        songs.add(new Song("Senhor Meu Deus", 155, "", "I\n" +
                "\n" +
                "Senhor meu Deus\n" +
                "Quando eu maravilhado\n" +
                "Os grandes feitos vejo da tua mão\n" +
                "Estrela mundos e trovões \n" +
                "Rolando a proclamar\n" +
                "\n" +
                " Coro\n" +
                "\n" +
                "Canta minha’lma\n" +
                "Então a ti Senhor\n" +
                "Grandioso és tu\n" +
                "Canta minha’lma\n" +
                "Então a ti Senhor \n" +
                "Grandioso és tu\n" +
                "Grandioso és tu\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Quando atravesso Bosques e Florestas\n" +
                "Ouvindo a brisa pássaros a Cantar\n" +
                "Ou vejo além montanhas alterneiras\n" +
                "O teu poder e glória proclamar.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Quando afinal em resplandor e glória\n" +
                "Jesus abrir as portas da Mansão\n" +
                "Eu quero estar de Joelhos entre os santos \n" +
                "Na mais humilde e vera adoração.\n"));

        songs.add(new Song("Oh Doce e Bela História", 156, "", "I\n" +
                "\n" +
                "Eu folgo em repeti-la - A história de Jesus \n" +
                "Qua da suprema glória - Baixou à amarga Cruz\n" +
                "Sim folgo em repeti-la pois ela satisfaz \n" +
                "As ânsias da minha alma - O mundo não o faz\n" +
                "\n" +
                "Coro\n" +
                "\n" +
                "Oh doce e bela história - De Cristo Salvador\n" +
                "De sua imensa graça - Do seu infinito Amor.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Eu folgo em repeti-lo que tal foi amor\n" +
                "Que por seu inimigo - Morreu o Salvador \n" +
                "Que Cristo ainda hoje - Com terna compaixão\n" +
                "Procura os pecadores - Oferece-lhes perdão\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Sim folgo em repeti-la - Pois há quem nunca ouviu\n" +
                "Da salvação de Cristo - Nem seu amor sentiu\n" +
                " E quando esse hino novo - Na glória eu cantar\n" +
                "Sempre esse amor imenso - Eu hei-de celebrar!\n"));

        songs.add(new Song("Louvor a Deus", 157, "", "I\n" +
                "\n" +
                "Quem pelo mundo transitar,\n" +
                "A Deus bendiga com prazer, \n" +
                "Saiba em espírito adorar\n" +
                "Aquele que lhe deu o ser.\n" +
                "\n" +
                "II\n" +
                "\n" +
                "Entrai na casa do Senhor \n" +
                "E seus louvores entoai; \n" +
                "Ovelhas sois do bom Pastor \n" +
                "E filhos do Celeste Pai.\n" +
                "\n" +
                "III\n" +
                "\n" +
                "Também sois servos de Jesus, \n" +
                "Guardai, portanto, a sua Lei; \n" +
                "Vivendo sempre em sua luz, \n" +
                "Seu santo nome engrandecei.\n" +
                "\n" +
                "IV\n" +
                "\n" +
                "A vida eterna nos dará\n" +
                "Por sua infinda compaixão; \n" +
                "O seu amor sem fim será;\n" +
                "Gloriosa a nossa redenção.\n"));

        songs.add(new Song("Eis-me Convosco!", 158, "", "I\n" +
                "Peregrinando por sobre os montes,\n" +
                "E pelos vales, Sempre na luz\n" +
                "Cristo pronome nunca deixar-me:\n" +
                "“Eis-me convosco”, disse Jesus.\n" +
                "\n" +
                "Coro\n" +
                "Brilho Celeste! Brilho Celeste\n" +
                "Enche a minha alma glória do Céu!\n" +
                "Aleluia! Sigo Cantando,\n" +
                "Dando louvores, pois Cristo é meu!\n" +
                "\n" +
                "II\n" +
                "Sombras à roda, sombras em cima,\n" +
                "O Salvador não hão de ocultar;\n" +
                "Ele é a luz, que nunca se apaga,\n" +
                "Bem ao seu lado, sempre hei de andar.\n" +
                "\n" +
                "III\n" +
                "A luz bendita me vai cercando\n" +
                "Em meu caminho para a Maansão;\n" +
                "Mais e mais perto seguindo o Mestre,\n" +
                "Possuo o gozo da salvação! – B. R. D.\n"));

        songs.add(new Song("Pastor Divino", 159, "", "I\n" +
                "Eis – nos, ó Pastor divino,\n" +
                "Todos juntos num lugar,\n" +
                "Como ovelhas, congregadas\n" +
                "Teu auxílio a suplicar\n" +
                "Sê presente (bis)\n" +
                "O rebalho o apascentar\n" +
                "\n" +
                "II\n" +
                "Guia os tristes, fatigados,\n" +
                "Ao aprisco do Senhor!\n" +
                "Leva os tenros cordeirnhos\n" +
                "Nos teus braços, bom Pastor,\n" +
                "Ás pastagens (bis)\n" +
                "De celeste e doce amor!\n" +
                "\n" +
                "III\n" +
                "Vem encher o teu rebanho\n" +
                "De sincera gratidão\n" +
                "Cantaremos (bis)\n" +
                "Tua imansa compaixão\n"));

        songs.add(new Song("Noite Jubilosa", 160, "", "I\n" +
                "Noite, jubilosa\n" +
                "Noite Portentosa\n" +
                "Doce Luz do Feliz Natal\n" +
                "Deus connosco habita\n" +
                "Cristo é Emmanuel\n" +
                "Louvor ao Deus Divinal\n" +
                "\n" +
                "II\n" +
                "Noite Jubilosa\n" +
                "Noite Portentosa\n" +
                "Doce Luz do Feliz Natal\n" +
                "Cristo nos liberta\n" +
                "Cristo Vida certa\n" +
                "Louvor ao Deus Divinal\n" +
                "\n" +
                "III\n" +
                "Noite Jubilosa\n" +
                "Noite Portentosa\n" +
                "Doce Luz do Feliz Natal\n" +
                "Anjos Cantam Hinos\n" +
                "Ao Rei Pequenino\n" +
                "Louvor ao Deus Divinal\n"));

        // Add songs to FirebaseStore
        CollectionReference reference = firestore.collection(AppConstants.CANTEMOS_BOOK);
        for (Song song : songs) {
            //reference.add(song);
            reference.document(song.getNumber() + "").set(song);
        }
    }

    protected void generateDataRhonga() {

        List<Song> songs = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        //Category number 1
        categories.add(new Category("TA KU GANDRELA XIKWEMBU", 1, "0", "I"));
        //Category number 1
        Category categoryI1 = new Category("Ta ku drumisa Hosi", 2, "I", "1");
        categories.add(categoryI1);
        songs.add(new Song(1, "Nkhensan Yehova", "", categoryI1, "C"));
        songs.add(new Song(2, "Muvumbi wa Vhanu", "F. Giardini", categoryI1, "G"));
        songs.add(new Song(3, "Hosi ya ku ketemuka", "", categoryI1, "E"));
        songs.add(new Song(4, "Tanani, matiko!", "L. Mason", categoryI1, "A"));
        songs.add(new Song(5, "A hi nkhenseni Yehova", "", categoryI1, "G"));
        songs.add(new Song(6, "Bongan Muhanyisi", "", categoryI1, "C"));
        songs.add(new Song(7, "Hi bonga hi mbilu", "", categoryI1, "A"));
        songs.add(new Song(8, "Yehova wa phatima", "Piter Ritter", categoryI1, "G"));
        songs.add(new Song(9, "Hi mani lwa leletiki dambu", "", categoryI1, "F"));
        songs.add(new Song(10, "Xikwembu hi Yehova", "Samuel Webbe", categoryI1, "F"));

        songs.add(new Song(11, "A hi tlangelen Yehova", "", categoryI1, "C"));
        songs.add(new Song(12, "Hi ta milengeni ya Hosi", "T. Clark", categoryI1, "F"));
        songs.add(new Song(13, "A misava i ya Yehova", "Jeremiah Clark", categoryI1, "A"));
        songs.add(new Song(14, "Oh Yehova, we Hosi Yeru", "", categoryI1, "D"));
        songs.add(new Song(15, "Drumisani Yehova", "Johann Crüger", categoryI1, "G"));
        songs.add(new Song(16, "Wa phatima", "Immler", categoryI1, "C"));
        songs.add(new Song(17, "Halleluya ku Yehova", "V. Schumann", categoryI1, "G"));
        songs.add(new Song(18, "Hosi ya matilo", "Ar Siciliano", categoryI1, "F"));
        songs.add(new Song(19, "A ribre dra Yakob", "C. Malan", categoryI1, "C"));
        songs.add(new Song(20, "Hi randra ku twalisa", "Beethoven", categoryI1, "C"));

        songs.add(new Song(21, "Ndri ta ku tlakuxa, Hosi", "J. Haydn", categoryI1, "A"));
        songs.add(new Song(22, "Hakunene hi ta yimbelela", "", categoryI1, "Ab"));
        songs.add(new Song(23, "Tlangela Yehova", "", categoryI1, "Eb"));
        songs.add(new Song(24, "khinsamelani Yesu", "Willian Shrubsole", categoryI1, "A"));
        songs.add(new Song(25, "Yimbelelani Hosi", "C. Malan", categoryI1, "Eb"));
        songs.add(new Song(26, "A hi bongeni Yesu", "", categoryI1, "C"));
        songs.add(new Song(27, "Yesu a fuma hinkwaswu", "L. Mason", categoryI1, "G"));
        songs.add(new Song(28, "Hinkwenu vhanu va tiko", "", categoryI1, "G"));

        songs.add(new Song(301, "Bongan, bongan", "", categoryI1, "C"));
        songs.add(new Song(302, "Xikwembu xi bekile", "", categoryI1, "Eb"));
        songs.add(new Song(303, "Yimbelelan ku Yehova", "", categoryI1, "F"));
        songs.add(new Song(304, "Yehova Xikwembu, Hosi ya matilo", "", categoryI1, "Eb"));
        songs.add(new Song(305, "A lirandru laku, Hosi", "", categoryI1, "C"));
        songs.add(new Song(306, "Hosi Yesu, lwa nga henhla", "", categoryI1, "Bb"));
        songs.add(new Song(307, "A hi yimbelelen tinsimu", "", categoryI1, "Bb"));

        //Category number 2
        Category categoryI2 = new Category("Ta siku dra Hosi(Sonto)", 2, "I", "2");
        categories.add(categoryI2);
        songs.add(new Song(29, "Hosi yi vitana vhanu", "", categoryI2, "C"));
        songs.add(new Song(30, "Kereke draku we Yesu", "J. G. Ebeling", categoryI2, "G"));
        songs.add(new Song(31, "Hinkwenu lava lulamiki", "", categoryI2, "E"));

        //Category number 3
        Category categoryI3 = new Category("Ta siku dra Xikwembu", 2, "I", "3");
        categories.add(categoryI3);
        songs.add(new Song(32, "Siku dra namunhla", "Ar espanhol", categoryI3, "C"));
        songs.add(new Song(33, "Hosi yanga, ndra ku djula", "P. Artomius", categoryI3, "C"));
        songs.add(new Song(34, "Swa nandrika ku ta trhama", "D. Bortniansky", categoryI3, "F"));
        songs.add(new Song(35, "Ku hundri viki drin'wana", "L. Spohr", categoryI3, "G"));
        songs.add(new Song(36, "Trhindrekelani vamakweru", "C. Malan", categoryI3, "Bb"));
        songs.add(new Song(308, "Dra nandrika siku ledri", "", categoryI3, "Bb"));

        //Category number 4
        Category categoryI4 = new Category("Ta Rito dra Xikwembu", 2, "I", "4");
        categories.add(categoryI4);
        songs.add(new Song(37, "Timbilwini teru", "Ar Silesiano", categoryI4, "F"));
        songs.add(new Song(38, "Timhaka ta ku hanyisa", "W. A. Mozart", categoryI4, "F"));
        songs.add(new Song(39, "Xihlayelamfuri", "Ar Silesiano", categoryI4, "C"));

        //Category number 5
        Category categoryI5 = new Category("Ta ku huma tinhlengeletanweni", 2, "I", "5");
        categories.add(categoryI5);
        songs.add(new Song(40, "Ku Yehova, Muhanyisi", "", categoryI5, "G"));
        songs.add(new Song(41, "Xikwembu xi hi heketa", "", categoryI5, "G"));
        songs.add(new Song(42, "Oh Xikwembu xa ku xwenga", "William Letton Viner", categoryI5, "A"));
        songs.add(new Song(43, "We Muhuluxi Yesu", "Edward John Hopkins", categoryI5, "A"));
        songs.add(new Song(44, "Xikwembu Mudumbeki", "Melchior Vulpius", categoryI5, "Eb"));
        songs.add(new Song(309, "Tatana ha ku tlangela", "T. Clark", categoryI5, "F"));

        //Category number 6
        Category categoryI6 = new Category("Ta vugandzeli", 2, "I", "6");
        categories.add(categoryI6);
        songs.add(new Song(45, "A hi twalisen Muvumbi", "Melchior Vulpius", categoryI6, "D"));
        songs.add(new Song(46, "Oh Hosi, u nga va na hine", "A. Mottu", categoryI6, "C"));
        songs.add(new Song(47, "Hi mine wa mudohi", "", categoryI6, "Eb"));
        songs.add(new Song(48, "We Hosi, hi tretrelele!", "A. Mottu", categoryI6, "C"));
        songs.add(new Song(49, "Hosi, hi yentxele vurombe", "", categoryI6, "C"));
        songs.add(new Song(50, "Wene hambana ya Xikwembu", "C. Malan", categoryI6, "C"));
        songs.add(new Song(51, "Landra Yesu", "J. Fawcett", categoryI6, "Ab"));
        songs.add(new Song(52, "Halleluya!", "Melchior Vulpius", categoryI6, "D"));
        songs.add(new Song(53, "A timpralu ta Hosi Yesu", "Chr. Gregor", categoryI6, "D"));

        //Category Level II
        categories.add(new Category("TA MINKHUVO YA KEREKE", 1, "0", "II"));
        //Category number 1
        Category categoryII1 = new Category("Ta ku belekiwa ka Hosi Yesu", 2, "II", "1");
        categories.add(categoryII1);
        songs.add(new Song(54, "Le mutini wa Davida", "Henry John Gauntlett", categoryII1, "G"));
        songs.add(new Song(55, "I man lwa yimbelelaka", "", categoryII1, "C"));
        songs.add(new Song(56, "Mutin wa Betlehema", "W. J. Kirkpatric", categoryII1, "F"));
        songs.add(new Song(57, "Xana ma mu tiva n'wana", "", categoryII1, "D"));
        songs.add(new Song(58, "Yingelan ritu drinene", "", categoryII1, "D"));
        songs.add(new Song(59, "Dri xongile siku ledri", "F. Gruber", categoryII1, "C"));
        songs.add(new Song(60, "Hlamalan! Hlamalan!", "", categoryII1, "F"));

        songs.add(new Song(61, "Nowell wa ku ranga", "Risimu ra Khinsimusi", categoryII1, "C"));
        songs.add(new Song(62, "A hi trhaveni!", "H. G. Naegeli", categoryII1, "C"));
        songs.add(new Song(310, "Aleluya! A hi tsakeni", "", categoryII1, "A"));
        songs.add(new Song(311, "I vusiku, hinkwaku!", "", categoryII1, "C"));
        songs.add(new Song(312, "Hangweyesan, vapfumeli", "", categoryII1, "C"));

        //Category number 2
        Category categoryII2 = new Category("Ta ku khangula lembe", 2, "II", "2");
        categories.add(categoryII2);
        songs.add(new Song(63, "Swoswi lembe dri heliki", "", categoryII2, "Ab"));
        songs.add(new Song(64, "Tatana, u vumbi a vhanu", "William Croft", categoryII2, "C"));
        songs.add(new Song(313, "Lembe drimprha", "", categoryII2, "Eb"));

        //Category number 3
        Category categoryII3 = new Category("Ta siku dra mahanga", 2, "II", "3");
        categories.add(categoryII3);
        songs.add(new Song(65, "Hosi yi ya nhingena mutini", "", categoryII3, "D"));
        songs.add(new Song(66, "Yi ta ku wene Hosi ya ku", "Hamburger M. Handbuch", categoryII3, "C"));
        songs.add(new Song(67, "A hi twalisen Hosi Yesu", "", categoryII3, "A"));

        //Category number 4
        Category categoryII4 = new Category("Ta ku fa ka Hosi Yesu", 2, "II", "4");
        categories.add(categoryII4);
        songs.add(new Song(68, "Hi yimbelela Yesu", "L. Mason", categoryII4, "G"));
        songs.add(new Song(69, "Lifu laku, Muhanyisi", "E. Miller", categoryII4, "Eb"));
        songs.add(new Song(70, "I man lweyi a konyaka", "", categoryII4, "Eb"));
        songs.add(new Song(71, "Lavisa xihambano", "S. S. Wesley", categoryII4, "Eb"));
        songs.add(new Song(72, "Hambana ya Xikwembu", "S. S. Wesley", categoryII4, "Eb"));
        songs.add(new Song(73, "Lavisa, makweru, xihambano", "R. Lowry", categoryII4, "G"));
        songs.add(new Song(74, "Hosi Yesu, we makweru", "Palestrina", categoryII4, "D"));
        songs.add(new Song(75, "Ho! we, xihambano", "", categoryII4, "C"));
        songs.add(new Song(76, "Vonan, hi lwe Nandra wa Yehova", "G. F. Handel", categoryII4, "F"));
        songs.add(new Song(77, "Muprofeta wa Yehova", "Swazilandia", categoryII4, "E"));
        songs.add(new Song(78, "We, Muvumbiwa weru", "L. Hassler", categoryII4, "D"));
        songs.add(new Song(79, "Gandrela Yesu xihambanweni", "E. W. Bullinger", categoryII4, "A"));
        songs.add(new Song(80, "Lifu laku we Hosi Yesu", "Freylinghausen", categoryII4, "Bb"));
        songs.add(new Song(81, "Vonani Yesu Getsemane", "H. G. Naegeli", categoryII4, "G"));

        //Category number 5
        Category categoryII5 = new Category("Ta ku pfuka ka Hosi Yesu", 2, "II", "5");
        categories.add(categoryII5);
        songs.add(new Song(82, "Yesu a pfukile ku fen", "", categoryII5, "C"));
        songs.add(new Song(83, "Halleluya! a pfukile", "", categoryII5, "G"));
        songs.add(new Song(84, "A pfukile, Mutiruli weru", "F. S. Laur", categoryII5, "C"));
        songs.add(new Song(85, "A hluli xira Yesu", "Melchior Teschner", categoryII5, "C"));
        songs.add(new Song(86, "Ha ku drumisa", "G. F. Handel", categoryII5, "C"));
        songs.add(new Song(87, "Yesu Kriste a pfukile", "Lyra Davidica", categoryII5, "C"));
        songs.add(new Song(314, "Vamakweru, i siku dra Paska", "", categoryII5, "C"));

        //Category number 6
        Category categoryII6 = new Category("Ta ku tlhantuka ka Hosi Yesu", 2, "II", "6");
        categories.add(categoryII6);
        songs.add(new Song(88, "Twalisani Yesu", "", categoryII6, "D"));
        songs.add(new Song(89, "A hi twalisen Hosi Yesu", "G. F. Handel", categoryII6, "G"));
        songs.add(new Song(90, "Hosi Yesu a hluli xira", "Negro Spiritual", categoryII6, "F"));

        //Category number 7
        Category categoryII7 = new Category("Ta Moya lowa ku xwenga", 2, "II", "7");
        categories.add(categoryII7);
        songs.add(new Song(91, "Moya wanga wa ku djula", "Ph. Nicolai", categoryII7, "F"));
        songs.add(new Song(92, "Tana Moya wa ku xwenga", "", categoryII7, "Eb"));
        songs.add(new Song(93, "Oho! Moya wa Yehova", "G. J. Vogler", categoryII7, "A"));
        songs.add(new Song(94, "A mutini wa le Sion", "G. Makavi", categoryII7, "D"));
        songs.add(new Song(95, "Moya lowu pfaka tilweni", "Th. Bovet", categoryII7, "E"));
        songs.add(new Song(96, "Hi vavisiwa hi mumu", "", categoryII7, "Bb"));

        //Category number 8
        Category categoryII8 = new Category("Ta masimu", 2, "II", "8");
        categories.add(categoryII8);
        songs.add(new Song(97, "Tiko dra kweru", "W. H. Monk", categoryII8, "Eb"));
        songs.add(new Song(98, "Ha ku nkhensa Nkulukumba", "M. E. M.", categoryII8, "G"));
        songs.add(new Song(99, "Loko xidrimu xi tlhasa", "D. C. Marivate", categoryII8, "Eb"));
        songs.add(new Song(100, "A timbewu teru", "M. E. M.", categoryII8, "C"));

        //Category Level III
        categories.add(new Category("TA KU HANYA KA VANA VA XIKWEMBU", 1, "0", "III"));
        //Category number 1
        Category categoryIII1 = new Category("Ta ku vitana vanhu", 2, "III", "1");
        categories.add(categoryIII1);
        songs.add(new Song(101, "N'wine lava pfaka tikweni", "", categoryIII1, "C"));
        songs.add(new Song(102, "N'wana wa lipfalu", "", categoryIII1, "Bb"));
        songs.add(new Song(103, "We makweru!", "", categoryIII1, "F"));
        songs.add(new Song(104, "Mubrisi lwenene", "", categoryIII1, "A"));
        songs.add(new Song(105, "Tana ku Yesu, u nga hlweli", "G. F. Root", categoryIII1, "Bb"));
        songs.add(new Song(106, "Tana, tana", "W. H. Doane", categoryIII1, "Eb"));
        songs.add(new Song(107, "Henhla ka lwandle", "Ph. P. Bliss", categoryIII1, "Bb"));
        songs.add(new Song(108, "Yingela, we, makweru", "", categoryIII1, "E"));
        songs.add(new Song(109, "Yesu a yimi nyangweni", "", categoryIII1, "C"));
        songs.add(new Song(110, "Landra Yesu", "J. Fawcett", categoryIII1, "Ab"));

        songs.add(new Song(111, "Hi mine, ndri yima nyangweni", "", categoryIII1, "F"));
        songs.add(new Song(112, "A vutomin lebri taka", "", categoryIII1, "F"));
        songs.add(new Song(113, "Yo! vakweru lavanyingi", "", categoryIII1, "G"));
        songs.add(new Song(114, "Wene N'wana wa Davida", "Daniel Read", categoryIII1, "G"));
        songs.add(new Song(115, "Wen, u drilaka", "Ph. P. Bliss", categoryIII1, "Bb"));
        songs.add(new Song(116, "Yingelani! Yingelani!", "Geo. F. Root", categoryIII1, "Eb"));
        songs.add(new Song(117, "Hosi yi ku vitanile", "", categoryIII1, "Ab"));
        songs.add(new Song(118, "N'wine n'wi djulaka vutomi", "", categoryIII1, "E"));
        songs.add(new Song(119, "Trhindrekela! Trhindrekela!", "Negro Spiritual", categoryIII1, "F"));

        songs.add(new Song(120, "Vamatiko yingelani", "I. McGranahan", categoryIII1, "G"));
        songs.add(new Song(121, "Xana n'wa dri tiva", "Sankey", categoryIII1, "F"));
        songs.add(new Song(122, "Ndri ya tilweni", "Negro Spiritual", categoryIII1, "E"));
        songs.add(new Song(123, "Loko n'wi djula ku tiva", "C. Brentano", categoryIII1, "F"));
        songs.add(new Song(315, "Yingela mhaka ya Hosi", "", categoryIII1, "C"));

        //Category number 2
        Category categoryIII2 = new Category("Ta ku tisola ni ku pfumela", 2, "III", "2");
        categories.add(categoryIII2);
        songs.add(new Song(124, "Hosi Yesu, lavisa", "", categoryIII2, "D"));
        songs.add(new Song(125, "U nkulukumba Yehova", "J. B. Dykes", categoryIII2, "Eb"));
        songs.add(new Song(126, "Ndri ni khombo, ndri ta ntsena", "", categoryIII2, "G"));
        songs.add(new Song(127, "Ndri ni khombo, ndri ta ntsena", "Bradbury", categoryIII2, "Eb"));
        songs.add(new Song(128, "We, Xikwembu Tatana!", "E. A. J. Monaisa", categoryIII2, "Eb"));
        songs.add(new Song(129, "Hosi, Yesu, a swi kone", "", categoryIII2, "C"));
        songs.add(new Song(130, "Hi mine wa mudohi", "", categoryIII2, "Eb"));

        songs.add(new Song(131, "Ndri ta ku wene, Hosi", "G. Coll", categoryIII2, "F"));
        songs.add(new Song(132, "Ndra tilaya, Hosi Yesu", "", categoryIII2, "A"));
        songs.add(new Song(133, "Ku yendren kwanga misaven", "", categoryIII2, "G"));
        songs.add(new Song(134, "Hinkweru vhanu hi dohile", "Sir. H. Oakeley", categoryIII2, "A"));
        songs.add(new Song(135, "Ndri drivakekiwile", "S. Malale", categoryIII2, "F"));
        songs.add(new Song(136, "A wu te Mfumo waku", "Melchior Teschner", categoryIII2, "C"));
        songs.add(new Song(137, "Ndri xanisiwa hi swidoho swanga", "Sankey-Stebbins", categoryIII2, "A"));
        songs.add(new Song(138, "Yingelan n'wine vamakweru!", "D. C. Marivate", categoryIII2, "D"));
        songs.add(new Song(316, "Oho, Yesu, Hosi yanga", "", categoryIII2, "C"));

        //Category number 3
        Category categoryIII3 = new Category("Ta ku dumba Muhuluxi", 2, "III", "3");
        categories.add(categoryIII3);
        songs.add(new Song(139, "Ndra ku nkhensa", "", categoryIII3, "F"));
        songs.add(new Song(140, "Ndra swi randra ku yingela wene", "C. H. Purday", categoryIII3, "G"));
        songs.add(new Song(141, "Ndri n'wanaku", "", categoryIII3, "Ab"));
        songs.add(new Song(142, "Ndri beleka hi Yesu", "W. H. Doane", categoryIII3, "G"));
        songs.add(new Song(143, "Makweru, ndri ku kumile", "Frank", categoryIII3, "F"));
        songs.add(new Song(144, "Yesu, Mutiruli", "R. Lowry", categoryIII3, "Ab"));
        songs.add(new Song(145, "Swa nadrika, Hosi yanga", "", categoryIII3, "Ab"));
        songs.add(new Song(146, "Tiko ledri dri tele swihono", "", categoryIII3, "F"));
        songs.add(new Song(147, "Mbilu yanga yi kota ku rula", "G. T. Caldbeck", categoryIII3, "C"));
        songs.add(new Song(148, "Ndri dumbi yene ntsena", "", categoryIII3, "D"));
        songs.add(new Song(149, "Kusuhi na wene", "L. Mason", categoryIII3, "G"));
        songs.add(new Song(150, "Ku Yesu ndri lavisela", "F. Mendelssohn", categoryIII3, "G"));

        songs.add(new Song(151, "Yesu, Hosi ya lirandru", "John Stainer", categoryIII3, "E"));
        songs.add(new Song(152, "Hinkwaku na Yesu", "", categoryIII3, "Eb"));
        songs.add(new Song(153, "Yesu wa ndri tiva", "Negro Spiritual", categoryIII3, "F"));
        songs.add(new Song(154, "Yesu, hi wene kunene", "", categoryIII3, "E"));
        songs.add(new Song(155, "A mintlhan'wini lemi suvisaka", "W. H. Monk", categoryIII3, "Eb"));
        songs.add(new Song(156, "Ndri tlakuxa moya wanga", "", categoryIII3, "G"));
        songs.add(new Song(157, "Yehova ndri mu laviseli", "Robert Simpson", categoryIII3, "A"));
        songs.add(new Song(158, "Namunhla ndri kumile xinyonxiso", "Jean Sibelius", categoryIII3, "F"));
        songs.add(new Song(159, "Ndri fihle a khokholweni", "Hartsough", categoryIII3, "Eb"));
        songs.add(new Song(160, "A hosi la a makari kweru", "", categoryIII3, "E"));
        songs.add(new Song(161, "Ku wene Oh Yesu", "D. Bortniansky", categoryIII3, "C"));

        //Category number 4
        Category categoryIII4 = new Category("Ta ku nyoxela ku huluxiwa", 2, "III", "4");
        categories.add(categoryIII4);
        songs.add(new Song(162, "Dra nandrika", "", categoryIII4, "A"));
        songs.add(new Song(163, "Nkhensa Hika dranga", "R. T. Caluza", categoryIII4, "G"));
        songs.add(new Song(164, "Ndri kumile nakulori", "", categoryIII4, "A"));
        songs.add(new Song(165, "Ritu dri twala henhla", "Dykes", categoryIII4, "Ab"));
        songs.add(new Song(166, "Ndri ta nkhensa hi masiku", "", categoryIII4, "D"));
        songs.add(new Song(167, "Yingelani ritu drikulu", "McGranahan", categoryIII4, "D"));
        songs.add(new Song(168, "A hi trhaven, vamakweru", "", categoryIII4, "A"));
        songs.add(new Song(169, "Yesu a ndri huluxile", "Ch. H. Gabriel", categoryIII4, "A"));
        songs.add(new Song(317, "Fa ndri lahlekile", "", categoryIII4, "C"));

        //Category number 5
        Category categoryIII5 = new Category("Ta ku tinyiketa ka Muhuluxi", 2, "III", "5");
        categories.add(categoryIII5);
        songs.add(new Song(170, "Xikwembu, we Hosi, yanga", "", categoryIII5, "F"));
        songs.add(new Song(171, "Mutiruli ni Hosi yanga", "R. W. Beaty", categoryIII5, "F"));
        songs.add(new Song(172, "Tamela voko dranga", "F. Silcher", categoryIII5, "F"));
        songs.add(new Song(173, "Swi xongi ku ku tirela", "M. Russell", categoryIII5, "Eb"));
        songs.add(new Song(174, "Moya ni miri yanga", "J. F. Knapp", categoryIII5, "Eb"));
        songs.add(new Song(175, "Ndra ku djula we he mixo", "F. Mendelssohn", categoryIII5, "D"));
        songs.add(new Song(176, "Hosi Yesu, mutiruli", "", categoryIII5, "E"));
        songs.add(new Song(177, "Mbilwin yanga, Hosi Yesu", "", categoryIII5, "G"));
        songs.add(new Song(178, "Yesu, lwey u ndri feliki", "F. A. Schulz", categoryIII5, "F"));
        songs.add(new Song(179, "Ndri trimbe, wene Hosi", "G. W. Martin", categoryIII5, "E"));
        songs.add(new Song(180, "Ndri djula ku va mhunu wa Yesu", "Negro Spiritual", categoryIII5, "F"));

        songs.add(new Song(181, "Ndri nyiki Tatana!", "P. P. Bliss", categoryIII5, "Eb"));
        songs.add(new Song(182, "Mbilwin yanga, Hosi", "John Knox Bokwe", categoryIII5, "F"));
        songs.add(new Song(183, "Ndri n'wanaku, Hosi Yesu", "", categoryIII5, "Eb"));
        songs.add(new Song(184, "O Yesu, Hosi ya timpralu", "A. L. Peace", categoryIII5, "Ab"));
        songs.add(new Song(185, "Tatana wa lirandru", "", categoryIII5, "E"));
        songs.add(new Song(318, "Yesu lwa ndri randraka", "", categoryIII5, "Eb"));
        songs.add(new Song(319, "Ndlelen ya ku ndri randra", "", categoryIII5, "A"));

        //Category number 6
        Category categoryIII6 = new Category("Ta ku lwa ni ku hlula ka kereke", 2, "III", "6");
        categories.add(categoryIII6);
        songs.add(new Song(186, "Siman Lisimu la yimpi", "T. R. Southgate", categoryIII6, "G"));
        songs.add(new Song(187, "Dumba Yesu, mukriste", "", categoryIII6, "F"));
        songs.add(new Song(188, "Pfukan, n'wi tinhena!", "Ch. Lockhark", categoryIII6, "Eb"));
        songs.add(new Song(189, "Hi siku dra khombo", "H. R. Bishop", categoryIII6, "F"));
        songs.add(new Song(190, "Pfukan he! tinhena!", "A. S. Sullivan", categoryIII6, "F"));

        songs.add(new Song(191, "Yesu i Mufumi weru", "", categoryIII6, "G"));
        songs.add(new Song(192, "Lihlampfu leru hi Yesu", "M. Luther", categoryIII6, "D"));
        songs.add(new Song(193, "Hloma matlhari, we mukriste", "John Hatton", categoryIII6, "Eb"));
        songs.add(new Song(194, "Hitekani, loko tilo dri xile", "S. Zuberbuhler", categoryIII6, "C"));
        songs.add(new Song(195, "A ku na mhunu", "Negro Spititual", categoryIII6, "G"));
        songs.add(new Song(196, "Mbilwin yanga Hosi", "Swazilandia", categoryIII6, "G"));
        songs.add(new Song(197, "Yimpi leyi hi yi lwaka", "J. Haydn", categoryIII6, "G"));
        songs.add(new Song(198, "Xidzedze", "D. C. Marivate", categoryIII6, "G"));

        //Category number 7
        Category categoryIII7 = new Category("Mahlomulweni", 2, "III", "7");
        categories.add(categoryIII7);
        songs.add(new Song(199, "Timhaka leta ku bindra", "Converse", categoryIII7, "F"));
        songs.add(new Song(200, "Trhika wene, moya wanga", "", categoryIII7, "E"));

        songs.add(new Song(201, "Bratrhu dri famba ha hombe", "", categoryIII7, "C"));
        songs.add(new Song(202, "Hosi ni tatana wanga", "", categoryIII7, "Eb"));
        songs.add(new Song(203, "We Yehova, Hosi yanga", "", categoryIII7, "F"));
        songs.add(new Song(204, "Ndri mufambi misaveni", "Hastings", categoryIII7, "D"));
        songs.add(new Song(205, "Swanga mhuti yi drilaka", "", categoryIII7, "G"));
        songs.add(new Song(206, "Yentxa, Hosi yanga", "C. M. Weber", categoryIII7, "Eb"));
        songs.add(new Song(207, "A ku vilela hinkwaku", "Negro Spiritual", categoryIII7, "G"));
        songs.add(new Song(208, "Yingela, Hosi, n'wanaku", "R. Lowry", categoryIII7, "G"));
        songs.add(new Song(320, "Hloma yimpi ya Hosi", "G. T. Webb", categoryIII7, "F"));
        songs.add(new Song(321, "A ku khumba nkhantxu wakwe", "", categoryIII7, "D"));
        songs.add(new Song(322, "Loko ndri vaviseka", "", categoryIII7, "F"));

        //Category number 8
        Category categoryIII8 = new Category("Ta ku hlangana na Hosi Yesu", 2, "III", "8");
        categories.add(categoryIII8);
        songs.add(new Song(209, "Yesu, fuma mbilu yanga", "", categoryIII8, "Ab"));
        songs.add(new Song(210, "Yesu, Yesu, We Mutiruli", "J. Brahms", categoryIII8, "Eb"));

        songs.add(new Song(211, "Ntrhurini wa misava leyi", "A. Bost", categoryIII8, "C"));
        songs.add(new Song(212, "Lirandru la Yesu Kriste", "", categoryIII8, "C"));
        songs.add(new Song(213, "A hi tlangelen lirandru", "", categoryIII8, "D"));
        songs.add(new Song(214, "Wa ndri randra", "", categoryIII8, "G"));

        //Category number 9
        Category categoryIII9 = new Category("Ta ku rhurha ni ta vutomi bya tilo", 2, "III", "9");
        categories.add(categoryIII9);
        songs.add(new Song(215, "Muti lo xongiki ngopfu", "G. F. Handel", categoryIII9, "F"));
        songs.add(new Song(216, "Tatana wa ndri vita", "", categoryIII9, "F"));
        songs.add(new Song(217, "Hi nkhensa lifu la Yesu", "", categoryIII9, "Eb"));
        songs.add(new Song(218, "Ndri nga muyeni lomu", "Melodia Popular", categoryIII9, "D"));
        songs.add(new Song(219, "Masiku yeru ma fana hinkwawu", "", categoryIII9, "C"));
        songs.add(new Song(220, "Masiku yanga ma hela", "R. Lowry", categoryIII9, "Eb"));

        songs.add(new Song(221, "A tilweni!", "Ch. F. Voigtlaender", categoryIII9, "C"));
        songs.add(new Song(222, "Drimukani kaya tilwen", "", categoryIII9, "A"));
        songs.add(new Song(223, "Tiku dra Xikwembu dri kone", "", categoryIII9, "G"));
        songs.add(new Song(224, "A hi suken, hi muka kanana", "", categoryIII9, "F"));
        songs.add(new Song(225, "Yerusalema wa le henhla", "", categoryIII9, "Eb"));
        songs.add(new Song(226, "Xa i vaman lava hi va vonaka", "", categoryIII9, "Eb"));
        songs.add(new Song(227, "Vaweti! Hi welela", "", categoryIII9, "A"));
        songs.add(new Song(228, "Swa nandrika ku drimuka", "", categoryIII9, "F"));
        songs.add(new Song(229, "Ku ni tiku draku xonga", "", categoryIII9, "Eb"));
        songs.add(new Song(230, "Lavisani Hosi Yesu ni tintrumi", "Swazilandia", categoryIII9, "C"));

        songs.add(new Song(323, "Xana vamakweru", "", categoryIII9, "F"));
        songs.add(new Song(324, "Swoswi hi sukile", "", categoryIII9, "G"));
        songs.add(new Song(325, "Vhanu va matiko hinkwawu", "", categoryIII9, "C"));


        //Category Level IV
        categories.add(new Category("TA KEREKE NI MINDANGU NI TIKO", 1, "0", "IV"));
        //Category number 1
        Category categoryIV1 = new Category("Ta ku tekana", 2, "IV", "1");
        categories.add(categoryIV1);
        songs.add(new Song(231, "Xawan Vatekani", "Corelli", categoryIV1, "C"));
        songs.add(new Song(232, "Hosi, yamukela swoswi", "J. B. Dykes", categoryIV1, "D"));
        songs.add(new Song(233, "A khale loko fa hi vatrongwana", "Ph. P. Bliss", categoryIV1, "D"));
        songs.add(new Song(234, "Hi nkhensile hi timbilu", "J. B. Dykes", categoryIV1, "C"));
        songs.add(new Song(235, "Xikwembu, vateki lava", "", categoryIV1, "Eb"));
        songs.add(new Song(236, "N'wi vamakweru, ha mi tlangela", "", categoryIV1, "D"));

        //Category number 2
        Category categoryIV2 = new Category("Ta ku trakamisa ni ku yamukela vana", 2, "IV", "2");
        categories.add(categoryIV2);
        songs.add(new Song(237, "Tiya, nakulori", "I. Pleyel", categoryIV2, "Bb"));
        songs.add(new Song(238, "Hosi Yesu, yamukela swoswi", "", categoryIV2, "Eb"));
        songs.add(new Song(239, "Le Galileya, Hosi Yesu", "I. D. Sankey", categoryIV2, "Eb"));
        songs.add(new Song(240, "Tiyisa, mutrakamisiwa", "William Boyd", categoryIV2, "G"));
        songs.add(new Song(326, "A makari kweru", "", categoryIV2, "F"));

        //Category number 3
        Category categoryIV3 = new Category("Ta ntiro wa Xilalelo", 2, "IV", "3");
        categories.add(categoryIV3);
        songs.add(new Song(241, "Yesu kateko dra timbilu", "H. P. Smith", categoryIV3, "E"));
        songs.add(new Song(242, "U hi phamelile", "P. A. Bost", categoryIV3, "G"));
        songs.add(new Song(243, "Ndra ku hlalela wene", "", categoryIV3, "F"));
        songs.add(new Song(244, "Yesu a fambile", "H. G. Naegeli", categoryIV3, "G"));
        songs.add(new Song(245, "Ndri trhindrekela wene", "R. Saillens", categoryIV3, "G"));

        //Category number 4
        Category categoryIV4 = new Category("Ta ku khanguriwa ka yindlu ya Xikwembu", 2, "IV", "4");
        categories.add(categoryIV4);
        songs.add(new Song(246, "Hi tlangela ngopfu vatiri", "T. R. Matthews", categoryIV4, "Eb"));
        songs.add(new Song(247, "Namunhla, siku ledri kulu", "A. L. Peace", categoryIV4, "Ab"));
        songs.add(new Song(248, "Famban, na mi tlakuxa muhlu", "J. Bortnianski", categoryIV4, "C"));
        songs.add(new Song(249, "A hi nkhensen, a hi bongeni", "", categoryIV4, "Bb"));
        songs.add(new Song(250, "Ha drimuka kambe", "D. C. Marivate", categoryIV4, "Eb"));
        songs.add(new Song(327, "Malandra ya Yehova", "", categoryIV4, "F"));
        songs.add(new Song(328, "Hi yimbelela tiko", "", categoryIV4, "C"));


        //Category number 5
        Category categoryIV5 = new Category("Ta mixo ni madambu", 2, "IV", "5");
        categories.add(categoryIV5);
        songs.add(new Song(251, "Yehova Xikwembu", "R. Lowry", categoryIV5, "G"));
        songs.add(new Song(252, "Vusiku bri hundrile", "L. Mason", categoryIV5, "F"));
        songs.add(new Song(253, "Leswi adambu dri humiki", "L. Neiss", categoryIV5, "Eb"));
        songs.add(new Song(254, "N'winyi wa ku vonekisa", "W. Bradbury", categoryIV5, "Ab"));
        songs.add(new Song(255, "Leswi dambu dri peliki", "W. Bradbury", categoryIV5, "Ab"));
        songs.add(new Song(256, "Trhama, Hosi, leswi dri peliki", "W. H. Monk", categoryIV5, "Eb"));
        songs.add(new Song(257, "Tatan, vana va Tatana", "D. Bortniansky", categoryIV5, "F"));
        songs.add(new Song(258, "Namunhla siku dri helile", "C. C. Scholefield", categoryIV5, "A"));
        songs.add(new Song(259, "Tanan, hi hlengeletana", "", categoryIV5, "G"));
        songs.add(new Song(260, "Ndra khongota, dri pelile", "Geo. Stebbings", categoryIV5, "A"));


        //Category number 6
        Category categoryIV6 = new Category("Ta mindangu", 2, "IV", "6");
        categories.add(categoryIV6);
        songs.add(new Song(261, "Ku xonga amiti leyi", "D. Bortniansky", categoryIV6, "Eb"));
        songs.add(new Song(262, "Ku kateka mhunu", "F. J. Haydn", categoryIV6, "G"));
        songs.add(new Song(263, "Hosi, amindangu yeru", "Hurndall", categoryIV6, "F"));


        //Category number 7
        Category categoryIV7 = new Category("Ta Lavapswa", 2, "IV", "7");
        categories.add(categoryIV7);
        songs.add(new Song(264, "N'wi madyaha, drimuakan!", "", categoryIV7, "G"));
        songs.add(new Song(265, "Antamu wa mbilu yanga", "", categoryIV7, "F"));
        songs.add(new Song(266, "Tinhena ta Hosi", "Adeste Fideles", categoryIV7, "G"));
        songs.add(new Song(267, "Oh! Yesu Muhanyisi", "Negro Spiritual", categoryIV7, "F"));
        songs.add(new Song(268, "Wokani andrilo!", "", categoryIV7, "F"));
        songs.add(new Song(269, "Loko hi tlhangana", "J. P. E. Hartmann", categoryIV7, "F"));
        songs.add(new Song(270, "Ndri navela ku tirela Yesu", "", categoryIV7, "F"));

        songs.add(new Song(271, "A ritu dra Xikwembu", "", categoryIV7, "F"));
        songs.add(new Song(272, "A drin'wana siku", "", categoryIV7, "F"));
        songs.add(new Song(273, "Hi dondrise ku yentxa", "", categoryIV7, "G"));
        songs.add(new Song(274, "We, Davida, u yimba harpa", "", categoryIV7, "F"));
        songs.add(new Song(275, "Daniel hi lweyi", "", categoryIV7, "D"));

        //Category number 8
        Category categoryIV8 = new Category("Ta ku hlangana ka timbilu ta lava ku kateka", 2, "IV", "8");
        categories.add(categoryIV8);
        songs.add(new Song(276, "Hi li: Xawan", "", categoryIV8, "C"));
        songs.add(new Song(277, "Hosi a yimi heketeni", "", categoryIV8, "D"));
        songs.add(new Song(278, "A hi nkhensen nhlengeletanu", "C. Malan", categoryIV8, "C"));
        songs.add(new Song(279, "Ku nandrika ka wo ntlhanganu", "R. T. Caluza", categoryIV8, "Bb"));


        //Category number 9
        Category categoryIV9 = new Category("Ta ku fambisa Rito", 2, "IV", "9");
        categories.add(categoryIV9);
        songs.add(new Song(280, "Hangwesan miya va vita", "", categoryIV9, "F"));
        songs.add(new Song(281, "Va lomu ni vamatiko", "", categoryIV9, "C"));
        songs.add(new Song(282, "Brala", "", categoryIV9, "C"));
        songs.add(new Song(283, "Pfuka, yimpi ya Yesu", "G. T. Webb", categoryIV9, "Bb"));
        songs.add(new Song(284, "A hi pfuken, Hi ya lwela", "", categoryIV9, "C"));
        songs.add(new Song(285, "Lavisan tihambana", "", categoryIV9, "Eb"));
        songs.add(new Song(286, "Pfuka, yimpi ya Yehova", "", categoryIV9, "G"));
        songs.add(new Song(287, "Huwa ya le Makedoniya", "", categoryIV9, "Eb"));
        songs.add(new Song(329, "I wa yini, nkosi lowu", "", categoryIV9, "C"));
        songs.add(new Song(330, "Hloma yimpi ya Hosi!", "G. T. Webb", categoryIV9, "Bb"));

        //Category number 10
        Category categoryIV10 = new Category("Ta ku buya ka Hosi Yesu", 2, "IV", "10");
        categories.add(categoryIV10);
        songs.add(new Song(288, "Hi lwa taka, Hosi Yesu", "Clark", categoryIV10, "Eb"));
        songs.add(new Song(289, "Tana, Oh Yesu", "H. Lutteroth", categoryIV10, "Eb"));
        songs.add(new Song(290, "Pfukan, mi hiteka swoswi", "G. F. Handel", categoryIV10, "Eb"));

        //Category number 11
        Category categoryIV11 = new Category("Ta tiko", 2, "IV", "11");
        categories.add(categoryIV11);
        songs.add(new Song(291, "A tiko dra kweru", "D. Bortnianski", categoryIV11, "C"));
        songs.add(new Song(292, "Loko Yehova afa a hi trhika", "Genevan Psalter", categoryIV11, "G"));
        songs.add(new Song(293, "Hosi katekisa Afrika!", "E. Sontonga", categoryIV11, "Eb"));
        songs.add(new Song(294, "Oh Hosi katekisa a tiko", "J. Dalcroze", categoryIV11, "C"));

        //Category number 12
        Category categoryIV12 = new Category("Ta vatitroni", 2, "IV", "12");
        categories.add(categoryIV12);
        songs.add(new Song(295, "Bandla ledrinene", "", categoryIV12, "Bb"));
        songs.add(new Song(296, "Yiman, vatitroni, yiman", "", categoryIV12, "G"));
        songs.add(new Song(297, "A hi famben, hi tiyisela", "", categoryIV12, "F"));
        songs.add(new Song(298, "Ndri famba na ndri trhavile", "Negro Spiritual", categoryIV12, "C"));
        songs.add(new Song(299, "A tiko dreru dra lahleka", "J. Hatton", categoryIV12, "Eb"));
        songs.add(new Song(300, "Yingelani n'wi vamakweru", "R. T. Caluza", categoryIV12, "Bb"));


        // Add songs to FirebaseStore
        CollectionReference reference = firestore.collection(AppConstants.TV_RONGA_BOOK);
        for (Song song : songs) {
            reference.document(song.getNumber() + "").set(song);
        }

        // Add categories to FirebaseStore
        CollectionReference categoryReference = firestore.collection(AppConstants.TV_CATEGORY_RG);
        for (Category category : categories) {
            categoryReference.add(category);
        }
    }

    /**
     * This methot is used to generate all songs belongs to tsonga book
     */
    protected void generateDataChangana() {
        List<Song> songs = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        //Super Category Level 1
        categories.add(new Category("TA KU GANDZELA XIKWEMBU", 1, "0", "I"));
        //Category number 1
        Category categoryI1 = new Category("Ta ku dzunisa Hosi", 2, "I", "1");
        categories.add(categoryI1);
        songs.add(new Song(1, "Nkhensani Yehova", "", categoryI1, "C"));
        songs.add(new Song(2, "Ka Mumbi wa vanhu", "F. Giardini", categoryI1, "G"));
        songs.add(new Song(3, "Hosi ya ku vonakala", "", categoryI1, "E"));
        songs.add(new Song(4, "Tanani, matiko!", "L. Mason", categoryI1, "A"));
        songs.add(new Song(5, "A hi nkenseni Yehova", "", categoryI1, "G"));
        songs.add(new Song(6, "Vongani Muhanyisi", "", categoryI1, "C"));
        songs.add(new Song(7, "Hi dzuna hi mbilu", "", categoryI1, "A"));
        songs.add(new Song(8, "Yehova wa saseka", "Piter Ritter", categoryI1, "G"));
        songs.add(new Song(9, "I mani la leriseke dyambu", "", categoryI1, "F"));
        songs.add(new Song(10, "Xikwembu i Yehova", "Samuel Webbe", categoryI1, "F"));

        songs.add(new Song(11, "A hi tlangeleni Yehova", "", categoryI1, "C"));
        songs.add(new Song(12, "Hi ta milengeni ya Hosi", "T. Clark", categoryI1, "F"));
        songs.add(new Song(13, "Misava i yakwe", "Jeremiah Clark", categoryI1, "A"));
        songs.add(new Song(14, "O! Yehova, Hosi ya hina", "", categoryI1, "D"));
        songs.add(new Song(15, "Dzunisani Yehova", "Johann Crüger", categoryI1, "G"));
        songs.add(new Song(16, "Wa kwetsima", "Immler", categoryI1, "C"));
        songs.add(new Song(17, "Aleluya ka Yehova", "V. Schumann", categoryI1, "G"));
        songs.add(new Song(18, "Hosi ya matilo", "Ar Siciliano", categoryI1, "F"));
        songs.add(new Song(19, "Khokholo ra hina", "C. Malan", categoryI1, "C"));
        songs.add(new Song(20, "Hi rhandza ku twarisa", "Beethoven", categoryI1, "C"));

        songs.add(new Song(21, "Ndzi ta ku kurisa, Hosi", "J. Haydn", categoryI1, "A"));
        songs.add(new Song(22, "Ha kunene hi ta yimbelela", "", categoryI1, "Ab"));
        songs.add(new Song(23, "Tlangela Yehova", "", categoryI1, "Eb"));
        songs.add(new Song(24, "Nkhinsamelani Yesu", "Willian Shrubsole", categoryI1, "A"));
        songs.add(new Song(25, "Yimbelelani Hosi", "C. Malan", categoryI1, "Eb"));
        songs.add(new Song(26, "A hi vongeni Yesu", "", categoryI1, "C"));
        songs.add(new Song(27, "Yesu wa fuma hinkwaswo", "L. Mason", categoryI1, "G"));
        songs.add(new Song(28, "Hinkwenu, vanhu va tiko", "", categoryI1, "G"));

        songs.add(new Song(301, "Vongani, vongani", "", categoryI1, "C"));
        songs.add(new Song(302, "Xikwembu xi vekile", "", categoryI1, "Eb"));
        songs.add(new Song(303, "Yimbelelani ka Yehova", "", categoryI1, "F"));
        songs.add(new Song(304, "Yehova Xikwembu, Hosi ya matilo", "", categoryI1, "Eb"));
        songs.add(new Song(305, "Rirhandzu ra wena, Hosi", "", categoryI1, "C"));
        songs.add(new Song(306, "Hosi Yesu, la nge henhla", "", categoryI1, "Bb"));
        songs.add(new Song(307, "A hi yimbeleleni tinsimu", "", categoryI1, "Bb"));

        //Category number 2
        Category categoryI2 = new Category("Ta ku sungula Sonto", 2, "I", "2");
        categories.add(categoryI2);
        songs.add(new Song(29, "Hosi yi vitana vanhu", "", categoryI2, "C"));
        songs.add(new Song(30, "Kereke ya wena, Yesu", "J. G. Ebeling", categoryI2, "G"));
        songs.add(new Song(31, "Hinkwenu lava lulameke", "", categoryI2, "E"));

        //Category number 3
        Category categoryI3 = new Category("Ta siku ra Xikwembu", 2, "I", "3");
        categories.add(categoryI3);
        songs.add(new Song(32, "Siku ra namuntlha", "Ar espanhol", categoryI3, "C"));
        songs.add(new Song(33, "Hosi yanga, ndza ku lava", "P. Artomius", categoryI3, "C"));
        songs.add(new Song(34, "Swa nandziha ku hlangana", "D. Bortniansky", categoryI3, "F"));
        songs.add(new Song(35, "Ku hundze vhiki rin'wana", "L. Spohr", categoryI3, "G"));
        songs.add(new Song(36, "Tshineleleni vamakwerhu", "C. Malan", categoryI3, "Bb"));
        songs.add(new Song(308, "Ra nandziha siku leri", "", categoryI3, "Bb"));

        //Category number 4
        Category categoryI4 = new Category("Ta Rito ra Xikwembu", 2, "I", "4");
        categories.add(categoryI4);
        songs.add(new Song(37, "Timbilwini ta hina", "Ar Silesiano", categoryI4, "F"));
        songs.add(new Song(38, "Timhaka leto hanyisa", "W. A. Mozart", categoryI4, "F"));
        songs.add(new Song(39, "Rito lero tiya ri na Yehova ntsena", "Ar Silesiano", categoryI4, "C"));

        //Category number 5
        Category categoryI5 = new Category("Ta ku huma tinhlengeletanweni", 2, "I", "5");
        categories.add(categoryI5);
        songs.add(new Song(40, "Ka Yehova Muhanyisi", "", categoryI5, "G"));
        songs.add(new Song(41, "Xikwembu, hi helekete", "", categoryI5, "G"));
        songs.add(new Song(42, "O! Xikwembu xo kwetsima", "William Letton Viner", categoryI5, "A"));
        songs.add(new Song(43, "We Muposinisi Yesu", "Edward John Hopkins", categoryI5, "A"));
        songs.add(new Song(44, "Xikwembu xo tshembeka", "Melchior Vulpius", categoryI5, "Eb"));
        songs.add(new Song(309, "Tatana, ha ku tlangela", "T. Clark", categoryI5, "F"));

        //Category number 6
        Category categoryI6 = new Category("Ta vugandzeli", 2, "I", "6");
        categories.add(categoryI6);
        songs.add(new Song(45, "A hi twariseni Muvumbi", "Melchior Vulpius", categoryI6, "D"));
        songs.add(new Song(46, "O! Hosi, u nga va", "A. Mottu", categoryI6, "C"));
        songs.add(new Song(47, "Hi mina wa mudyohi", "", categoryI6, "Eb"));
        songs.add(new Song(48, "Hosi, tsetselela hina!", "A. Mottu", categoryI6, "C"));
        songs.add(new Song(49, "Hosi, hi twele vusiwana", "", categoryI6, "C"));
        songs.add(new Song(50, "O! Xinyimpfana xa Xikwembu", "C. Malan", categoryI6, "C"));
        songs.add(new Song(51, "Landza Yesu", "J. Fawcett", categoryI6, "Ab"));
        songs.add(new Song(52, "Aleluya!", "Melchior Vulpius", categoryI6, "D"));
        songs.add(new Song(53, "Etintswalo ta Hosi ya hina", "Chr. Gregor", categoryI6, "D"));

        //Category Level II
        categories.add(new Category("TA MINKHUVO YA KEREKE", 1, "0", "II"));
        Category categoryII1 = new Category("Ta ku velekiwa ka Hosi Yesu", 2, "II", "1");
        //Category number 1
        categories.add(categoryII1);
        songs.add(new Song(54, "Le mutini wa Davida", "Henry John Gauntlett", categoryII1, "G"));
        songs.add(new Song(55, "I mani la yimbelelaka", "", categoryII1, "C"));
        songs.add(new Song(56, "Mutini wa Betlehema", "W. J. Kirkpatric", categoryII1, "F"));
        songs.add(new Song(57, "Xana ma n'wi tiva n'wana", "", categoryII1, "D"));
        songs.add(new Song(58, "Mi nga twa rito rinene", "", categoryII1, "D"));
        songs.add(new Song(59, "Ri xongile siku leri", "F. Gruber", categoryII1, "C"));
        songs.add(new Song(60, "Hlamalani, hlamalani!", "", categoryII1, "F"));

        songs.add(new Song(61, "Nowel ro sungula", "Risimu ra Khinsimusi", categoryII1, "C"));
        songs.add(new Song(62, "A hi tsakeni", "H. G. Naegeli", categoryII1, "C"));
        songs.add(new Song(310, "Aleluya! A hi tsakeni", "", categoryII1, "A"));
        songs.add(new Song(311, "I vusiku, hinkwako!", "", categoryII1, "C"));
        songs.add(new Song(312, "Hatlisani, vapfumeri", "", categoryII1, "C"));

        //Category number 2
        Category categoryII2 = new Category("Ta ku khangula lembe", 2, "II", "2");
        categories.add(categoryII2);
        songs.add(new Song(63, "Leswi lembe ri heleke", "", categoryII2, "Ab"));
        songs.add(new Song(64, "Tatana, u endlile vanhu", "William Croft", categoryII2, "C"));
        songs.add(new Song(313, "Lembe rintshwa", "", categoryII2, "Eb"));

        //Category number 3
        Category categoryII3 = new Category("Ta siku ra mahanga", 2, "II", "3");
        categories.add(categoryII3);
        songs.add(new Song(65, "Hosi Yesu o nghena mutini", "", categoryII3, "D"));
        songs.add(new Song(66, "Yi ta ka wena Hosi yaku", "Hamburger M. Handbuch", categoryII3, "C"));
        songs.add(new Song(67, "A hi twariseni Hosi Yesu", "", categoryII3, "A"));

        //Category number 4
        Category categoryII4 = new Category("Ta ku fa ka Hosi Yesu", 2, "II", "4");
        categories.add(categoryII4);
        songs.add(new Song(68, "Hi yimbelela Yesu", "L. Mason", categoryII4, "G"));
        songs.add(new Song(69, "Rifu raku, Muhanyisi", "E. Miller", categoryII4, "Eb"));
        songs.add(new Song(70, "I mani loya khunaraka", "", categoryII4, "Eb"));
        songs.add(new Song(71, "Languta Xihambano", "S. S. Wesley", categoryII4, "Eb"));
        songs.add(new Song(72, "Nyimpfana ya Xikwembu", "S. S. Wesley", categoryII4, "Eb"));
        songs.add(new Song(73, "Languta, makwerhu, languta", "R. Lowry", categoryII4, "G"));
        songs.add(new Song(74, "Yesu, makwerhu wa mina", "Palestrina", categoryII4, "D"));
        songs.add(new Song(75, "O! we, xihambano", "", categoryII4, "C"));
        songs.add(new Song(76, "Vonani, hi loyi nandza wa Yehova", "G. F. Handel", categoryII4, "F"));
        songs.add(new Song(77, "Muprofeta wa Yehova", "Swazilandia", categoryII4, "E"));
        songs.add(new Song(78, "Muvambiwa wa hina", "L. Hassler", categoryII4, "D"));
        songs.add(new Song(79, "Gandzela Yesu xihambanweni", "E. W. Bullinger", categoryII4, "A"));
        songs.add(new Song(80, "Rifu ra wena, Hosi Yesu", "Freylinghausen", categoryII4, "Bb"));
        songs.add(new Song(81, "Vonani Yesu Getsemane!", "H. G. Naegeli", categoryII4, "G"));

        //Category number 5
        Category categoryII5 = new Category("Ta ku pfuka ka Hosi Yesu", 2, "II", "5");
        categories.add(categoryII5);
        songs.add(new Song(82, "Yesu o pfukile ku feni", "", categoryII5, "C"));
        songs.add(new Song(83, "Aleluya! O pfukile!", "", categoryII5, "G"));
        songs.add(new Song(84, "O pfukile, Mukutsuri werhu", "F. S. Laur", categoryII5, "C"));
        songs.add(new Song(85, "O hlule sirha Yesu", "Melchior Teschner", categoryII5, "C"));
        songs.add(new Song(86, "Ha ku dzunisa", "G. F. Handel", categoryII5, "C"));
        songs.add(new Song(87, "Yesu Kriste o pfukile", "Lyra Davidica", categoryII5, "C"));
        songs.add(new Song(314, "Vamakwerhu, i siku ra Paska", "", categoryII5, "C"));

        //Category number 6
        Category categoryII6 = new Category("Ta ku tlhandluka ka Hosi Yesu", 2, "II", "6");
        categories.add(categoryII6);
        songs.add(new Song(88, "Twarisani Yesu", "", categoryII6, "D"));
        songs.add(new Song(89, "A hi twariseni Hosi Yesu", "G. F. Handel", categoryII6, "G"));
        songs.add(new Song(90, "Hosi Yesu a hlule sirha", "Negro Spiritual", categoryII6, "F"));

        //Category number 7
        Category categoryII7 = new Category("Ta Moya lowo kwetsima", 2, "II", "7");
        categories.add(categoryII7);
        songs.add(new Song(91, "Moya wanga wa ku lava", "Ph. Nicolai", categoryII7, "F"));
        songs.add(new Song(92, "Tana, Moya wo saseka", "", categoryII7, "Eb"));
        songs.add(new Song(93, "Oho, Moya wa Yehova", "G. J. Vogler", categoryII7, "A"));
        songs.add(new Song(94, "Emutini wa le Sion", "G. Makavi", categoryII7, "D"));
        songs.add(new Song(95, "Moya lowu humaka tilweni", "Th. Bovet", categoryII7, "E"));
        songs.add(new Song(96, "Hi vavisiwa hi mumu", "", categoryII7, "Eb"));

        //Category number 8
        Category categoryII8 = new Category("Ta masimu", 2, "II", "8");
        categories.add(categoryII8);
        songs.add(new Song(97, "Tiko ra hina", "W. H. Monk", categoryII8, "Eb"));
        songs.add(new Song(98, "Ha ku nkhensa", "M. E. M.", categoryII8, "G"));
        songs.add(new Song(99, "Loko xirimo xi fika", "D. C. Marivate", categoryII8, "Eb"));
        songs.add(new Song(100, "Timbewu ta hina", "M. E. M.", categoryII8, "C"));

        //Category Level III
        categories.add(new Category("TA KU HANYA KA VANA VA XIKWEMBU", 1, "0", "III"));
        //Category number 1
        Category categoryIII1 = new Category("Ta ku vitana vanhu", 2, "III", "1");
        categories.add(categoryIII1);
        songs.add(new Song(101, "N'wina lami humaka tikweni", "", categoryIII1, "C"));
        songs.add(new Song(102, "N'wana wa ripfalo", "", categoryIII1, "Bb"));
        songs.add(new Song(103, "Nakulorhi!", "", categoryIII1, "F"));
        songs.add(new Song(104, "Murisi lonene", "", categoryIII1, "A"));
        songs.add(new Song(105, "Tana ka Yesu, u nga hlweri", "G. F. Root", categoryIII1, "Bb"));
        songs.add(new Song(106, "Tana, tana", "W. H. Doane", categoryIII1, "Eb"));
        songs.add(new Song(107, "Henhla ka lwandle", "Ph. P. Bliss", categoryIII1, "Bb"));
        songs.add(new Song(108, "Yingisa, we makwerhu", "", categoryIII1, "E"));
        songs.add(new Song(109, "Yesu wa gongondza nyangweni", "", categoryIII1, "C"));
        songs.add(new Song(110, "Landza Yesu", "J. Fawcett", categoryIII1, "Ab"));

        songs.add(new Song(111, "Hi mina, ndzi yima nyangweni", "", categoryIII1, "F"));
        songs.add(new Song(112, "Vutomini lebyi nga ta ta", "", categoryIII1, "F"));
        songs.add(new Song(113, "Yo! Vakwerhu lavanyingi", "", categoryIII1, "G"));
        songs.add(new Song(114, "Wena N'wana wa Davida", "Daniel Read", categoryIII1, "G"));
        songs.add(new Song(115, "Wena, u rilaka", "Ph. P. Bliss", categoryIII1, "Bb"));
        songs.add(new Song(116, "Yingisani, Yingisani", "Geo. F. Root", categoryIII1, "Eb"));
        songs.add(new Song(117, "Hosi yi ku vitanile", "", categoryIII1, "Ab"));
        songs.add(new Song(118, "Hinkwenu vo lava vutomi", "", categoryIII1, "E"));
        songs.add(new Song(119, "Tshinelela! tshinelela!", "Negro Spiritual", categoryIII1, "F"));

        songs.add(new Song(120, "Vamatiko Yingisani", "I. McGranahan", categoryIII1, "G"));
        songs.add(new Song(121, "Xana mi tiva?", "Sankey", categoryIII1, "F"));
        songs.add(new Song(122, "Ndzi ya tilweni!", "Negro Spiritual", categoryIII1, "E"));
        songs.add(new Song(123, "Loko mi lava ku tiva", "C. Brentano", categoryIII1, "F"));
        songs.add(new Song(315, "Yingisa mhaka ya Hosi", "", categoryIII1, "C"));

        //Category number 2
        Category categoryIII2 = new Category("Ta ku tisola ni ku pfumela", 2, "III", "2");
        categories.add(categoryIII2);
        songs.add(new Song(124, "Hosi, hi tisa swirilo", "", categoryIII2, "D"));
        songs.add(new Song(125, "U nkulukumba, Yehova!", "J. B. Dykes", categoryIII2, "Eb"));
        songs.add(new Song(126, "Ndzi ni khombo, ndzi ta ntsena", "", categoryIII2, "G"));
        songs.add(new Song(127, "Ndzi ni khombo, ndzi ta ntsena", "Bradbury", categoryIII2, "Eb"));
        songs.add(new Song(128, "We, Xikwembu Tatana", "E. A. J. Monaisa", categoryIII2, "Eb"));
        songs.add(new Song(129, "Hosi Yesu, a swi kona", "", categoryIII2, "C"));
        songs.add(new Song(130, "Hi mina wa mudyohi", "", categoryIII2, "Eb"));

        songs.add(new Song(131, "Ndzi ta ka wena, Hosi", "G. Coll", categoryIII2, "F"));
        songs.add(new Song(132, "Ndza tilaya, Nkulukumba", "", categoryIII2, "A"));
        songs.add(new Song(133, "Rendzwen'ra mina, misaveni", "", categoryIII2, "G"));
        songs.add(new Song(134, "Hinkwerhu vanhu hi dyohile", "Sir. H. Oakeley", categoryIII2, "A"));
        songs.add(new Song(135, "Ndzi rivaleriwile", "S. Malale", categoryIII2, "F"));
        songs.add(new Song(136, "A wu te nfumo waku", "Melchior Teschner", categoryIII2, "C"));
        songs.add(new Song(137, "Ndzi xanisiwa hi swivi swanga", "Sankey-Stebbins", categoryIII2, "A"));
        songs.add(new Song(138, "Yingisani, n'wina vamakwerhu!", "D. C. Marivate", categoryIII2, "D"));
        songs.add(new Song(316, "Oho, Yesu, Hosi yanga", "", categoryIII2, "C"));

        //Category number 3
        Category categoryIII3 = new Category("Ta ku tshemba Muponisi", 2, "III", "3");
        categories.add(categoryIII3);
        songs.add(new Song(139, "Ndza ku nkhensa", "", categoryIII3, "F"));
        songs.add(new Song(140, "Ndza swi rhandza ku yingisa wena", "C. H. Purday", categoryIII3, "G"));
        songs.add(new Song(141, "Ndzi wa wena", "", categoryIII3, "Ab"));
        songs.add(new Song(142, "Ndzi veleka hi Yesu", "W. H. Doane", categoryIII3, "G"));
        songs.add(new Song(143, "Kunene ndzi swi kumile", "Frank", categoryIII3, "F"));
        songs.add(new Song(144, "Yesu, Mukutsuri", "R. Lowry", categoryIII3, "Ab"));
        songs.add(new Song(145, "Swa nandziha, Hosi yanga", "", categoryIII3, "Ab"));
        songs.add(new Song(146, "Tiko leri ri tele swo biha", "", categoryIII3, "F"));
        songs.add(new Song(147, "Mbilu yanga yi kota ku rhula", "G. T. Caldbeck", categoryIII3, "C"));
        songs.add(new Song(148, "Ndzi tshembile Murise", "", categoryIII3, "D"));
        songs.add(new Song(149, "Kusuhi na wena", "L. Mason", categoryIII3, "G"));
        songs.add(new Song(150, "Ka Yesu ndzi langutile", "F. Mendelssohn", categoryIII3, "G"));

        songs.add(new Song(151, "Yesu, Hosi ya rirhandzu", "John Stainer", categoryIII3, "F"));
        songs.add(new Song(152, "Hinkwako na Yesu", "", categoryIII3, "Eb"));
        songs.add(new Song(153, "Yesu a ndzi tiva", "Negro Spiritual", categoryIII3, "F"));
        songs.add(new Song(154, "Yesu, hi wena kunene", "", categoryIII3, "E"));
        songs.add(new Song(155, "Emintlhan'wini leyi lovisaka", "W. H. Monk", categoryIII3, "Eb"));
        songs.add(new Song(156, "Ndzi tlakisa moya wanga", "", categoryIII3, "G"));
        songs.add(new Song(157, "Yehova ndzi n'wi langutele", "Robert Simpson", categoryIII3, "A"));
        songs.add(new Song(158, "Namuntlha ndzi kumile xitsakiso", "Jean Sibelius", categoryIII3, "F"));
        songs.add(new Song(159, "Ndzi nghenise khokholweni", "Hartsough", categoryIII3, "Eb"));
        songs.add(new Song(160, "Hosi yi le xikarhi ka hina", "", categoryIII3, "E"));
        songs.add(new Song(161, "Ka wena, o! Yesu!", "D. Bortniansky", categoryIII3, "C"));

        //Category number 4
        Category categoryIII4 = new Category("Ta ku tsakela ku pona", 2, "III", "4");
        categories.add(categoryIII4);
        songs.add(new Song(162, "Ra nandziha", "", categoryIII4, "A"));
        songs.add(new Song(163, "Nkhensa, moya wanga", "R. T. Caluza", categoryIII4, "G"));
        songs.add(new Song(164, "Ndzi kumile nakulorhi", "", categoryIII4, "A"));
        songs.add(new Song(165, "Ndzi twa rito rin'wana", "Dykes", categoryIII4, "Ab"));
        songs.add(new Song(166, "Ndzi ta nkhensa hi masiku", "", categoryIII4, "D"));
        songs.add(new Song(167, "Twanani rito lerikulu", "McGranahan", categoryIII4, "D"));
        songs.add(new Song(168, "A hi tsakeni, vamakwerhu", "", categoryIII4, "A"));
        songs.add(new Song(169, "Yesu o ndzi ponisile", "Ch. H. Gabriel", categoryIII4, "A"));
        songs.add(new Song(317, "A ndzi lahlekile", "", categoryIII4, "C"));

        //Category number 5
        Category categoryIII5 = new Category("Ta ku tinyiketa ka Muponisi", 2, "III", "5");
        categories.add(categoryIII5);
        songs.add(new Song(170, "Xikwembu, Hosi ya mina", "", categoryIII5, "F"));
        songs.add(new Song(171, "Mukutsuri, Hosi ya mina", "R. W. Beaty", categoryIII5, "F"));
        songs.add(new Song(172, "Khoma voko ra mina", "F. Silcher", categoryIII5, "F"));
        songs.add(new Song(173, "Swa nandziha ku ku landza", "M. Russel", categoryIII5, "Eb"));
        songs.add(new Song(174, "Moya ni miri wanga", "J. F. Knapp", categoryIII5, "Eb"));
        songs.add(new Song(175, "Ndza ku lava, bya ha ku xa", "F. Mendelssohn", categoryIII5, "D"));
        songs.add(new Song(176, "Hosi Yesu, Muhanyisi", "", categoryIII5, "E"));
        songs.add(new Song(177, "Mbilwini ya mina, Yesu", "", categoryIII5, "G"));
        songs.add(new Song(178, "Yesu, loya ndzi feleke", "F. A. Schulz", categoryIII5, "F"));
        songs.add(new Song(179, "Ndzi bohe, wena Hosi", "G. W. Martin", categoryIII5, "E"));
        songs.add(new Song(180, "Ndzi lava ku va munhu wa Yesu", "Negro Spiritual", categoryIII5, "F"));

        songs.add(new Song(181, "Ndzi nyike, Tatana!", "P. P. Bliss", categoryIII5, "Eb"));
        songs.add(new Song(182, "Mbilwini yanga, Hosi", "John Knox Bokwe", categoryIII5, "F"));
        songs.add(new Song(183, "Ndzi wa wena, Hosi Yesu", "", categoryIII5, "Eb"));
        songs.add(new Song(184, "O! Yesu, Hosi ya tintswalo!", "A. L. Peace", categoryIII5, "Ab"));
        songs.add(new Song(185, "Tatana wa rirhandzu", "", categoryIII5, "E"));
        songs.add(new Song(318, "Yesu la ndzi rhandzaka", "", categoryIII5, "Eb"));
        songs.add(new Song(319, "Ndleleni yaku, ndzi rhandza", "", categoryIII5, "A"));

        //Category number 6
        Category categoryIII6 = new Category("Ta ku lwa ni ku hlula ka kereke", 2, "III", "6");
        categories.add(categoryIII6);
        songs.add(new Song(186, "Sumani tinsimu ta nyimpi", "T. R Southgate", categoryIII6, "G"));
        songs.add(new Song(187, "Tshemba Yesu, Mukriste", "", categoryIII6, "F"));
        songs.add(new Song(188, "Pfukani, n'we, tinhenha", "Ch. Lockhark", categoryIII6, "Eb"));
        songs.add(new Song(189, "Hi siku ra khombo", "H. R. Bishop", categoryIII6, "F"));
        songs.add(new Song(190, "Phalalani, tinhenha", "A. S. Sullivan", categoryIII6, "F"));

        songs.add(new Song(191, "Yesu i Hosi ya hina", "", categoryIII6, "G"));
        songs.add(new Song(192, "Rihlampfu ranga i Yesu", "M. Luther", categoryIII6, "D"));
        songs.add(new Song(193, "Hloma matlhari", "John Hatton", categoryIII6, "Eb"));
        songs.add(new Song(194, "Hitekani, loko dyambu ri xile", "S. Zuberbuhler", categoryIII6, "C"));
        songs.add(new Song(195, "A ku na munhu", "Negro Spiritual", categoryIII6, "G"));
        songs.add(new Song(196, "Mbilwini yanga Hosi!", "Swazilandia", categoryIII6, "G"));
        songs.add(new Song(197, "Nyimpi leyi hi yi lwaka", "J. Haydn", categoryIII6, "G"));
        songs.add(new Song(198, "Xidzedze", "D. C. Marivate", categoryIII6, "G"));

        //Category number 7
        Category categoryIII7 = new Category("Mahlomulweni", 2, "III", "7");
        categories.add(categoryIII7);
        songs.add(new Song(199, "Timhaka leti tikaka", "Converse", categoryIII7, "F"));
        songs.add(new Song(200, "Tshika, wena, moya wanga", "", categoryIII7, "E"));

        songs.add(new Song(201, "Byatso byi famba swinene", "", categoryIII7, "C"));
        songs.add(new Song(202, "Hosi ni Tatana wa mina", "", categoryIII7, "Eb"));
        songs.add(new Song(203, "We, Yehova, Hosi yanga", "", categoryIII7, "F"));
        songs.add(new Song(204, "Ndzi mufambi misaveni", "Hastings", categoryIII7, "D"));
        songs.add(new Song(205, "Ku kotisa mhalamhala", "", categoryIII7, "G"));
        songs.add(new Song(206, "Endla, Hosi yanga", "C. M. Weber", categoryIII7, "Eb"));
        songs.add(new Song(207, "Eku vilela hinkwako", "Negro Spiritual", categoryIII7, "G"));
        songs.add(new Song(208, "Yingisa, Hosi, n'wanaku", "R. Lowry", categoryIII7, "G"));
        songs.add(new Song(320, "U nga heli mbilu", "G. T. Webb", categoryIII7, "F"));
        songs.add(new Song(321, "A khumba ntsena nkhancu", "", categoryIII7, "D"));
        songs.add(new Song(322, "Loko ndzi vaviseka", "", categoryIII7, "F"));

        //Category number 8
        Category categoryIII8 = new Category("Ta ku hlangana na Hosi Yesu", 2, "III", "8");
        categories.add(categoryIII8);
        songs.add(new Song(209, "Yesu, fuma mbilu yanga", "", categoryIII8, "Ab"));
        songs.add(new Song(210, "Yesu! Yesu! Mukutsuri wa mina", "J. Brahms", categoryIII8, "Eb"));

        songs.add(new Song(211, "Ntshurini wa misava leyi", "A. Bost", categoryIII8, "C"));
        songs.add(new Song(212, "Rirhandzu ra Yesu Kriste", "", categoryIII8, "C"));
        songs.add(new Song(213, "A hi tlangeleni rirhandzu", "", categoryIII8, "D"));
        songs.add(new Song(214, "Wa ndzi rhandza", "", categoryIII8, "G"));

        //Category number 9
        Category categoryIII9 = new Category("Ta ku rhurha ni ta vutomi bya tilo", 2, "III", "9");
        categories.add(categoryIII9);
        songs.add(new Song(215, "Muti wo saseka ngopfu", "G. F. Handel", categoryIII9, "F"));
        songs.add(new Song(216, "Tatana wa vitana", "", categoryIII9, "F"));
        songs.add(new Song(217, "Hi nkhensa rifu ra Yesu", "", categoryIII9, "Eb"));
        songs.add(new Song(218, "Ndzi muendzi misaveni", "Melodia Popular", categoryIII9, "D"));
        songs.add(new Song(219, "Masiku yerhu ma fana", "", categoryIII9, "C"));
        songs.add(new Song(220, "Masiku yanga ma hela", "R. Lowry", categoryIII9, "Eb"));

        songs.add(new Song(221, "Le tilweni", "Ch. F. Volgtlaender", categoryIII9, "C"));
        songs.add(new Song(222, "Tsundzukani kaya tilweni", "", categoryIII9, "A"));
        songs.add(new Song(223, "Tiko ra Xikwembu ri kona", "", categoryIII9, "G"));
        songs.add(new Song(224, "A hi sukeni, hi lava Kanana", "", categoryIII9, "F"));
        songs.add(new Song(225, "Yerusalem wa le henhla", "", categoryIII9, "Eb"));
        songs.add(new Song(226, "M'bamani", "", categoryIII9, "Eb"));
        songs.add(new Song(227, "Vaweti, hi welela", "", categoryIII9, "A"));
        songs.add(new Song(228, "Swa nandziha ku tsundzuka", "", categoryIII9, "F"));
        songs.add(new Song(229, "Ku ni tiko ro saseka", "", categoryIII9, "Eb"));
        songs.add(new Song(230, "Langutani Hosi Yesu", "", categoryIII9, "C"));

        songs.add(new Song(323, "Xana vamakwerhu", "", categoryIII9, "F"));
        songs.add(new Song(324, "Sweswi hi sukile", "", categoryIII9, "G"));
        songs.add(new Song(325, "Vanhu hinkwavo va matiko", "", categoryIII9, "C"));


        //Category Level III
        categories.add(new Category("TA KEREKE NI DYANGU NI TIKO", 1, "0", "IV"));
        //Category number 1
        Category categoryIV1 = new Category("Ta ku tekana", 2, "IV", "1");
        categories.add(categoryIV1);
        songs.add(new Song(231, "Xewani, vatekani", "Corelli", categoryIV1, "C"));
        songs.add(new Song(232, "Hosi, amukela sweswi", "J. B. Dykes", categoryIV1, "D"));
        songs.add(new Song(233, "Khale loko a hi ri vatsanana", "Ph. P. Bliss", categoryIV1, "D"));
        songs.add(new Song(234, "Hi nkhensile hi timbilu", "J. B. Dykes", categoryIV1, "C"));
        songs.add(new Song(235, "Xikwembu, vateki lava", "", categoryIV1, "Eb"));
        songs.add(new Song(236, "N'we vamakwerhu, ha mi tlangela", "", categoryIV1, "D"));

        //Category number 2
        Category categoryIV2 = new Category("Ta ku khuvuriwa ka vapfumeri ni ta kuu vuyisa vana", 2, "IV", "2");
        categories.add(categoryIV2);
        songs.add(new Song(237, "Tiya, nakulorhi", "I. Pleyel", categoryIV2, "Bb"));
        songs.add(new Song(238, "Hosi Yesu, Murisi lonene", "", categoryIV2, "Eb"));
        songs.add(new Song(239, "Le Galilea", "I. D. Sankey", categoryIV2, "Eb"));
        songs.add(new Song(240, "Tiyisa, mukhuvuriwa", "William Boyd", categoryIV2, "G"));
        songs.add(new Song(326, "Xikarhi ka hina", "", categoryIV2, "F"));

        //Category number 3
        Category categoryIV3 = new Category("Ta Xilalelo", 2, "IV", "3");
        categories.add(categoryIV3);
        songs.add(new Song(241, "Yesu nkateko wa timbilu", "H. P. Smith", categoryIV3, "E"));
        songs.add(new Song(242, "U hi phamerile", "P. A. Bost", categoryIV3, "G"));
        songs.add(new Song(243, "Ndza ku hlalela, wena", "", categoryIV3, "F"));
        songs.add(new Song(244, "Yesu o fambile", "H. G. Naegeli", categoryIV3, "G"));
        songs.add(new Song(245, "Ndzi tshinelela wena", "R. Saillens", categoryIV3, "G"));

        //Category number 4
        Category categoryIV4 = new Category("Ta ku khanguriwa ka yindlu ya Xikwembu", 2, "IV", "4");
        categories.add(categoryIV4);
        songs.add(new Song(246, "Hi tlangerile, vatatana (vamamana)", "T. R. Matthews", categoryIV4, "Eb"));
        songs.add(new Song(247, "Namuntlha, siku lerikulu", "A. L. Peace", categoryIV4, "Ab"));
        songs.add(new Song(248, "Fambani, mi tlakusa matihlo", "J. Bortnianski", categoryIV4, "C"));
        songs.add(new Song(249, "A hi nkhenseni, a hi vongeni", "", categoryIV4, "Bb"));
        songs.add(new Song(250, "Mahungu lamanene", "D. C. Marivate", categoryIV4, "Eb"));
        songs.add(new Song(327, "Malandza ya Yehova", "", categoryIV4, "F"));
        songs.add(new Song(328, "Hi yimbelela tiko", "", categoryIV4, "C"));

        //Category number 5
        Category categoryIV5 = new Category("Ta mixo ni madyambu", 2, "IV", "5");
        categories.add(categoryIV5);
        songs.add(new Song(251, "Yehova Xikwembu", "R. Lowry", categoryIV5, "G"));
        songs.add(new Song(252, "Vusiku byi hundzile", "L. Mason", categoryIV5, "F"));
        songs.add(new Song(253, "Leswi dyambu ri tsuvukeke", "L. Neiss", categoryIV5, "Eb"));
        songs.add(new Song(254, "N'winyi wa ku vonakala", "W. Bradbury", categoryIV5, "Ab"));
        songs.add(new Song(255, "Leswi dyambu ri nga pela", "W. Bradbury", categoryIV5, "Ab"));
        songs.add(new Song(256, "Xwana, Hosi, hikuva ri perile", "W. H. Monk", categoryIV5, "Eb"));
        songs.add(new Song(257, "Tatani, vana va Tatana", "D. Bortniansky", categoryIV5, "F"));
        songs.add(new Song(258, "Namuntlha siku ri herile", "C. C. Scholefield", categoryIV5, "A"));
        songs.add(new Song(259, "Tanani, hi hlengeletana", "", categoryIV5, "G"));
        songs.add(new Song(260, "Ndza khongeala, ri perile", "Geo. Stebbings", categoryIV5, "A"));


        //Category number 6
        Category categoryIV6 = new Category("Ta ndyangu", 2, "IV", "6");
        categories.add(categoryIV6);
        songs.add(new Song(261, "Ku saseke miti leyi", "D. Bortniansky", categoryIV6, "Eb"));
        songs.add(new Song(262, "Ku kateka munhu", "F. J. Haydn", categoryIV6, "G"));
        songs.add(new Song(263, "Hosi, mindyangu ya hina", "Hurndall", categoryIV6, "F"));


        //Category number 7
        Category categoryIV7 = new Category("Ta lavantshwa", 2, "IV", "7");
        categories.add(categoryIV7);
        songs.add(new Song(264, "N'we madjaha, tsundzukani!", "", categoryIV7, "G"));
        songs.add(new Song(265, "Matimba ya mbilu", "", categoryIV7, "F"));
        songs.add(new Song(266, "Masocha ya Hosi", "Adeste Fideles", categoryIV7, "G"));
        songs.add(new Song(267, "O! Yesu, Muhanyisi!", "Negro Spiritual", categoryIV7, "F"));
        songs.add(new Song(268, "Okani endzilo", "", categoryIV7, "F"));
        songs.add(new Song(269, "Loko hi hlangana", "J. P. E. Hartmann", categoryIV7, "F"));
        songs.add(new Song(270, "Ndzi navela ku tirhela Yesu!", "", categoryIV7, "F"));

        songs.add(new Song(271, "E Rito ra Xikwembu", "", categoryIV7, "F"));
        songs.add(new Song(272, "Erin'wanyana siku", "", categoryIV7, "F"));
        songs.add(new Song(273, "Hi dyondzise ku endla", "", categoryIV7, "G"));
        songs.add(new Song(274, "We, Davida, u yimba harpa", "", categoryIV7, "F"));
        songs.add(new Song(275, "Daniel", "", categoryIV7, "D"));

        //Category number 8
        Category categoryIV8 = new Category("Ta ku hlangana ka timbilu ta lavo kwetsima", 2, "IV", "8");
        categories.add(categoryIV8);
        songs.add(new Song(276, "Hi li: Xawan", "", categoryIV8, "C"));
        songs.add(new Song(277, "Hosi a yimi heketeni", "", categoryIV8, "D"));
        songs.add(new Song(278, "A hi nkhensen nhlengeletanu", "C. Malan", categoryIV8, "C"));
        songs.add(new Song(279, "Ku nandrika ka wo ntlhanganu", "R. T. Caluza", categoryIV8, "Bb"));

        //Category number 9
        Category categoryIV9 = new Category("Ta ku fambisa Rito", 2, "IV", "9");
        categories.add(categoryIV9);
        songs.add(new Song(280, "Hatlisani! Mi ya va vitana", "", categoryIV9, "F"));
        songs.add(new Song(281, "Vamatiko va lovile", "", categoryIV9, "C"));
        songs.add(new Song(282, "Byala", "", categoryIV9, "C"));
        songs.add(new Song(283, "Pfuka, nyimpi ya Yesu", "G. T. Webb", categoryIV9, "Bb"));
        songs.add(new Song(284, "A hi pfukeni, Hi ya lwela", "", categoryIV9, "C"));
        songs.add(new Song(285, "Vonani swinyimpfana", "", categoryIV9, "Eb"));
        songs.add(new Song(286, "Pfuka, nyimpi ya Yehova", "", categoryIV9, "G"));
        songs.add(new Song(287, "Huwa ya Makedonia", "", categoryIV9, "Eb"));
        songs.add(new Song(329, "Twanani nkosi wa matiko", "", categoryIV9, "C"));
        songs.add(new Song(330, "Hloma nyimpi ya Hosi", "", categoryIV9, "Bb"));

        //Category number 10
        Category categoryIV10 = new Category("Ta ku vuya ka Hosi Yesu", 2, "IV", "10");
        categories.add(categoryIV10);
        songs.add(new Song(288, "Vonani, wa ta, Hosi Yesu", "Clark", categoryIV10, "Eb"));
        songs.add(new Song(289, "Tana, O! Yesu", "H. Lutteroth", categoryIV10, "Eb"));
        songs.add(new Song(290, "Pfukani, mi hiteka sweswi", "G. F. Handel", categoryIV10, "Eb"));

        //Category number 11
        Category categoryIV11 = new Category("Ta tiko", 2, "IV", "11");
        categories.add(categoryIV11);
        songs.add(new Song(291, "Etiko ra hina", "D. Bortnianski", categoryIV11, "C"));
        songs.add(new Song(292, "Loko Yehova ingi a hi tshika", "Genevan Psalter", categoryIV11, "G"));
        songs.add(new Song(293, "Hosi, katekisa Afrika!", "E. Sontonga", categoryIV11, "Eb"));
        songs.add(new Song(294, "O! Hosi, katekisa etiko", "J. Dalcroze", categoryIV11, "C"));

        //Category number 12
        Category categoryIV12 = new Category("Ta vatitsoni", 2, "IV", "12");
        categories.add(categoryIV12);
        songs.add(new Song(295, "Vandla lerinene", "", categoryIV12, "Bb"));
        songs.add(new Song(296, "Yimani, vatitsoni, yimani!", "", categoryIV12, "G"));
        songs.add(new Song(297, "A hi fambeni, hi tiyisela", "", categoryIV12, "F"));
        songs.add(new Song(298, "Ndzi famba na ndzi tsakile", "Negro Spiritual", categoryIV12, "C"));
        songs.add(new Song(299, "Tiko ra hina ra lahleka", "J. Hatton", categoryIV12, "Eb"));
        songs.add(new Song(300, "Yingisani n'we vamakwerhu", "R. T. Caluza", categoryIV12, "Bb"));

        // Add songs to FirebaseStore
        CollectionReference reference = firestore.collection(AppConstants.TV_TSONGA_BOOK);
        for (Song song : songs) {
            reference.document(song.getNumber() + "").set(song);
        }

        // Add categories to FirebaseStore
        CollectionReference categoryReference = firestore.collection(AppConstants.TV_CATEGORY_TG);
        for (Category category : categories) {
            categoryReference.add(category);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSongSelected(DocumentSnapshot song) {
        // Go to the details page for the selected restaurant

        Intent intent = new Intent(getActivity(), SongDetailActivity.class);
        intent.putExtra(AppConstants.BOOK, book);
        intent.putExtra(AppConstants.SONG_ID, song.getId());

        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bundleRecyclerViewState != null) {
            recyclerViewState = bundleRecyclerViewState.getParcelable(STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bundleRecyclerViewState = new Bundle();
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        bundleRecyclerViewState.putParcelable(STATE, recyclerViewState);
    }
}