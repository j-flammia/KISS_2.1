package com.example.kiss_21

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Today
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kiss_21.ui.theme.KISS_21Theme
import java.time.format.DateTimeFormatter


@Composable
fun KISS(context: Context) {
    DayViewScaffold(context)
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route
        ) {
            TestMainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailsScreen.route + "/{nameArg}",
            arguments = listOf(
                navArgument(name = "nameArg") {
                    type = NavType.StringType
                    defaultValue = "Jordan"
                    nullable = true
                }
            )
        ) {
                entry ->
            DetailsScreen(name = entry.arguments?.getString("nameArg"))
        }
    }
}

@Composable
fun TestMainScreen(navController: NavController) {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate(Screen.DetailsScreen.withArgs(text))
            },
            modifier = Modifier.align(Alignment.End)
        )
        {
            Text(text = "To Details Screen")
        }
    }
}

@Composable
fun DetailsScreen(name: String?) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Hello $name!")
    }
}

@Composable
fun DayViewScaffold(context: Context) {

    var day = "Today"

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.Magenta, contentColor = Color.White,
                title = {
                    Text(text = "$day", fontSize = 30.sp)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            val text = "This will open drawer!"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(context,text, duration)
                            toast.show()}
                    ){
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Main Menu")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val text = "This opens Calendar!"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(context,text, duration)
                            toast.show()}
                    ){
                        Icon(imageVector = Icons.Default.Today, contentDescription = "Calendar")
                    }
                }
            )
        },
        bottomBar = {

            Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray, contentColor = Color.Black),

                onClick = {
                    val text = "Opens material view!"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context,text, duration)
                    toast.show()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)) {
                Text(text = "Materials", modifier = Modifier.wrapContentSize(Alignment.Center), fontSize = 30.sp)
            }
        }
    ) { innerPadding ->
        DayViewContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun DayViewContent(modifier: Modifier = Modifier) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(20.dp))

        Project(
            project = ProjectClass("Smith Job"))

        Project(
            project = ProjectClass("Get Material for Tomorrow"))

        Project(
            project = ProjectClass("Hathaway"))

        Project(
            project = ProjectClass("Robertson Job"))
    }
}

@Composable
fun Project(project: ProjectClass) {}

// this is our BASIC EVENT to be displayed for each EVENT on the CALENDAR Page:
@Composable
fun BasicEvent (
    // this is the parameter we will pass EventClass's to:
    event: EventClass,
    // this is the parameter we will pass any additional modifiers to:
    modifier: Modifier = Modifier,

) {
    // declares a variable to use to format the start and end dates for each event:
    val EventTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")

    // defines each visual event object:
    Column(

        modifier = Modifier
            .wrapContentSize() // content will fill available size of contents allowable space.
            .padding(end = 2.dp, bottom = 2.dp) // internal padding
            // defines background color and shape of our events:
            .background(event.color, shape = RoundedCornerShape(4.dp))
            .padding(4.dp) // outside padding around each column

    ) {

        Text(
            // displays our start and end times:
            text =
            "${event.start.format(EventTimeFormatter)} - ${event.end.format(EventTimeFormatter)}",
            // sets the style for the event start and end times:
            style = MaterialTheme.typography.caption
        )

        Text(
            // displays the event name:
            text = event.name,
            // sets event name style:
            style = MaterialTheme.typography.body1,

        )
        // if there is a description for an event, this will add it to the content:
        if (event.description != null) {
            Text(
                // sets description text as event description text:
                text = event.description,
                // sets dexcription style:
                style = MaterialTheme.typography.body2,
                // max allowable lines for description:
                maxLines = 1,
                // sets the visual cue to show text has overflowed:
                overflow = TextOverflow.Ellipsis

            )
        }
    }
}

// this composable will display our daily column of events for calendar view:
@Composable
fun DayViewSchedule(
    eventsList: List<EventClass>, // this parameter takes a list of events
    modifier: Modifier = Modifier, // modifier parameters if necessary otherwise default
    // this parameter determines what to show for each eventClass in events list:
    eventContent: @Composable (event: EventClass) -> Unit = { BasicEvent(event = it) }
) {
    // this is our custom layout that will be used to visually arrange the events by time:
    Layout(
        content = { // this is the content the Layout will display:
            eventsList.sortedBy(EventClass::start).forEach { event ->
                eventContent(event)
            }
        },
    modifier = Modifier,
    ) {
        // this lambda will measure each event and keep track of total height used:
            measurables, constraints ->
        var height = 0
        val placeables = measurables.map { measurable ->
            // max height of each event is set to 64 dp:
            val placeable = measurable.measure(constraints.copy(maxHeight = 64.dp.roundToPx()))
            height += placeable.height
            placeable
        }
        // this will loop through each of the placeables and position them within the layout.
        // We keep track of the 'y' position so that they don't overlap:
        layout(constraints.maxWidth, height) {
            var y = 0
            placeables.forEach { placeable ->
                // places each event inline vertically, and stacked horizontally:
                placeable.place(0, y)
                y += placeable.height
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SchedulePreview() {
    KISS_21Theme() {
        DayViewSchedule(eventsList = sampleEvents)
    }
}

// override preview parameter so that it receives a list of events as arguments:
class EventsProviderClass : PreviewParameterProvider<EventClass> {
    override val values = sampleEvents.asSequence()
}

// preview for basic event:
@Preview(showBackground = true)
@Composable
fun EventPreview(
    @PreviewParameter(EventsProviderClass::class) event: EventClass
) {
    KISS_21Theme {
        BasicEvent(event, modifier = Modifier.sizeIn(maxHeight = 64.dp))
    }
}