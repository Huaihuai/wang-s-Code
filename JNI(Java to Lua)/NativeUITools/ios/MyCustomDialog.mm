//
//  MyCustomDialog.m
//  pinkture
//
//  Created by nemo on 12-11-6.
//
//

#import "MyCustomDialog.h"
#import "NUITools.h"

@implementation MyCustomDialog

-(id)init
{
    [self initUIRect];
    if ( self = [super initWithFrame:m_screenRect] )
    {
        [self initUIElement];
        
        textHolderSeted = NO;
        isKeyboardShowing = NO;
        m_textPlaceHolder = @"";
        btnResult = 0;
        textEditEventSent = false;
    }
    
    return self;
}

- (void)dealloc
{
    [contentTextView setDelegate:nil];
    [contentTextView release];
    [textViewBg release];
    //[nextButton autorelease];
    //[backButton autorelease];
    [panelView release];
    [super dealloc];
}

#pragma mark - Dialog Private Methods

-(void)initUIElement
{
    NSString* tCurrentlan = [self getCurrentLanguage];
    panelView = [[UIView alloc] initWithFrame:m_panelRect];
    [self addSubview:panelView];

// add back Button
    backButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [backButton setShowsTouchWhenHighlighted:YES];
    [backButton setFrame:m_backBtnRect];
    [backButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    if ([tCurrentlan isEqualToString:@"zh"]) {
        [backButton setImage:[UIImage imageNamed:@"back_btn_cn.png"] forState:UIControlStateNormal];
        [backButton setImage:[UIImage imageNamed:@"back_btn_hl_cn.png"] forState:UIControlStateHighlighted];
    }
    else
    {
        [backButton setImage:[UIImage imageNamed:@"back_btn.png"] forState:UIControlStateNormal];
        [backButton setImage:[UIImage imageNamed:@"back_btn_hl.png"] forState:UIControlStateHighlighted];
    }
    [backButton addTarget:self action:@selector(onBackButtonTouched:) forControlEvents:UIControlEventTouchUpInside];
    [panelView addSubview:backButton];
    
// add next Button
    nextButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [nextButton setShowsTouchWhenHighlighted:YES];
    [nextButton setFrame:m_nextBtnRect];
    [nextButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    if ([tCurrentlan isEqualToString:@"zh"]) {
        [nextButton setImage:[UIImage imageNamed:@"sent_btn_cn.png"] forState:UIControlStateNormal];
        [nextButton setImage:[UIImage imageNamed:@"sent_btn_hl_cn.png"] forState:UIControlStateHighlighted];
    }
    else{
        [nextButton setImage:[UIImage imageNamed:@"sent_btn.png"] forState:UIControlStateNormal];
        [nextButton setImage:[UIImage imageNamed:@"sent_btn_hl.png"] forState:UIControlStateHighlighted];
    }
    [nextButton addTarget:self action:@selector(onNextButtonTouched:) forControlEvents:UIControlEventTouchUpInside];
    [panelView addSubview:nextButton];
    
    
//set text view background
    textViewBg = [[UIImageView alloc] initWithFrame:m_contentTextBgRect];
    if (isIphone5) {
        textViewBg.image = [UIImage imageNamed:@"text_input_bg_ip5.png"];
    }
    else{
        textViewBg.image = [UIImage imageNamed:@"text_input_bg.png"];
    }
    [panelView addSubview:textViewBg];

//ad content text view
    contentTextView = [[UITextView alloc] initWithFrame:m_contentTextRect];
    [contentTextView setEditable:YES];
    [contentTextView setDelegate:self];
    [contentTextView setFont:[UIFont systemFontOfSize:16]];
    [contentTextView setBackgroundColor:[UIColor colorWithRed:1.0 green:1.0 blue:1.0 alpha:0.0]];
    contentTextView.textColor = [UIColor blackColor];
    [panelView addSubview:contentTextView];
    
    wordCountLabel = [[UILabel alloc] initWithFrame:m_wordCountLabelRect];
    [wordCountLabel setBackgroundColor:[UIColor clearColor]];
    [wordCountLabel setTextColor:[UIColor darkGrayColor]];
    [wordCountLabel setFont:[UIFont systemFontOfSize:16]];
    [wordCountLabel setTextAlignment:UITextAlignmentCenter];
    [panelView addSubview:wordCountLabel];
    
    clearTextButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [clearTextButton setShowsTouchWhenHighlighted:YES];
    [clearTextButton setFrame:m_clearTextBtnRect];
    [clearTextButton setContentMode:UIViewContentModeCenter];
    [clearTextButton setImage:[UIImage imageNamed:@"snsdg_delete.png"] forState:UIControlStateNormal];
    [clearTextButton addTarget:self action:@selector(onClearTextButtonTouched:) forControlEvents:UIControlEventTouchUpInside];
    [panelView addSubview:clearTextButton];
    
    [self calculateTextLength];
}

-(void)initUIRect
{
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (!window)
    {
        window = [[UIApplication sharedApplication].windows objectAtIndex:0];
    }
    m_screenRect = window.screen.bounds;
    
    if (m_screenRect.size.width == 768 &&  m_screenRect.size.height == 1024)
    {//ipad
        isIphone5 = false;
        m_panelRect = CGRectMake((768.0-288*2.0)/2, (1024-335*2.0)/2.0, 288*2, 335*2);
        m_contentTextRect = CGRectMake(13*2, 90*2, 262*2, 130*2);
        m_contentTextBgRect = CGRectMake(13*2, 90*2, 262*2, 130*2);
        m_backBtnRect = CGRectMake(30, 20, 48*2, 30*2);;
        m_nextBtnRect  = CGRectMake(440, 20, 48*2, 30*2);
    }
    else if (m_screenRect .size.width == 320 &&  m_screenRect .size.height == 480)
    {//iphone 3.5inch
        isIphone5 = false;
        m_panelRect = CGRectMake(0, 0, 320, 480);
        m_contentTextRect = CGRectMake(-2+16, 90-58+23, 300, 108);
        m_contentTextBgRect = CGRectMake(-6+16, 90-68+23, 304, 130);
        m_backBtnRect = CGRectMake(3, -3, 55, 45);
        m_nextBtnRect = CGRectMake(255, -3, 55, 45);
        m_wordCountLabelRect = CGRectMake(210+16, 190-75+23, 30, 30);
        m_clearTextBtnRect = CGRectMake(240+16, 191-75+23, 30, 30);
    }
    else if (m_screenRect .size.width == 320 &&  m_screenRect .size.height == 568 )
    {//iphone 4inch
        isIphone5 = true;
        int offsety = -15;
        m_panelRect = CGRectMake(0, 0, 320, 568);
        m_contentTextRect = CGRectMake(-2+16, 90-58+23-offsety, 300, 108+50);
        m_contentTextBgRect = CGRectMake(-6+16, 90-68+23-offsety, 304, 130+50);
        m_backBtnRect = CGRectMake(3, -3, 55, 45);
        m_nextBtnRect = CGRectMake(255, -3, 55, 45);
        
        m_clearTextBtnRect = CGRectMake(256, 189-offsety, 30, 30);
        m_wordCountLabelRect = CGRectMake(226, 188-offsety, 30, 30);
    }
        
}
-(NSString*)getCurrentLanguage
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSArray *languages = [defaults objectForKey:@"AppleLanguages"];
    NSString *currentLanguage = [languages objectAtIndex:0];
    
    // get the current language code.(such as English is "en", Chinese is "zh" and so on)
    NSDictionary* temp = [NSLocale componentsFromLocaleIdentifier:currentLanguage];
    NSString * languageCode = [temp objectForKey:NSLocaleLanguageCode];
    return languageCode;
}



#pragma mark Text Length

- (int)textLength:(NSString *)text
{
    float number = 0.0;
    for (int index = 0; index < [text length]; index++)
    {
        NSString *character = [text substringWithRange:NSMakeRange(index, 1)];
        
        if ([character lengthOfBytesUsingEncoding:NSUTF8StringEncoding] == 3)
        {
            number++;
        }
        else
        {
            number = number + 0.5;
        }
    }
    return ceil(number);
}

- (void)calculateTextLength
{
    int wordcount = 0;
    if ( !textHolderSeted ) {
        wordcount = [self textLength:contentTextView.text];
    }
    
	NSInteger count  = 90 - wordcount;
	if (count < 0)
    {
		[wordCountLabel setTextColor:[UIColor redColor]];
		[nextButton setEnabled:NO];
		[nextButton setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
	}
	else
    {
		[wordCountLabel setTextColor:[UIColor darkGrayColor]];
		[nextButton setEnabled:YES];
		[nextButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
	}
	
	[wordCountLabel setText:[NSString stringWithFormat:@"%i",count]];
}


#pragma mark - UITextViewDelegate Methods
#include "UserBS.h"
- (void)textViewDidBeginEditing:(UITextView *)textView
{
    if (textView == contentTextView) {
        [self RemoveTextPlaceHolder:textView];
    }
}

- (void)textViewDidEndEditing:(UITextView *)textView
{
    if (textView == contentTextView) {
        [self SetTextPlaceHolder:textView HolderText:m_textPlaceHolder];
    }
}

- (void)textViewDidChange:(UITextView *)textView
{
    if (textView == contentTextView) {
        [self calculateTextLength];
        if (textEditEventSent == false) {
            textEditEventSent = true;
            UserBS::UserBSLogEvent("EditTextByHandle", nil);
        }
    }
}

- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text
{
    return YES;
}

#pragma mark - UITextView fake Place holder Methods

-(void)SetTextPlaceHolder:(UITextView *)textView HolderText:(NSString*)holerText
{
    if( textHolderSeted )
    {
        return;
    }
    if ( [textView.text isEqualToString:@""] ) {
        textView.textColor = [UIColor grayColor];
        textView.text = holerText;
        textHolderSeted = true;
    }
}

-(void)RemoveTextPlaceHolder:(UITextView *)textView
{
    if( textHolderSeted )
    {
        textHolderSeted = false;
        textView.text = @"";
        textView.textColor = [UIColor blackColor];
    }
}

#pragma mark Actions

- (void)onBackButtonTouched:(id)sender
{
    btnResult = 1;
    NUITools::NUITools_CompletetCallback();
}
- (void)onNextButtonTouched:(id)sender
{
    btnResult = 2;
    NUITools::NUITools_CompletetCallback();
}

- (void)onClearTextButtonTouched:(id)sender
{
    
    [contentTextView setText:@""];
	[self calculateTextLength];
}

#pragma mark - UIKeyboardNotification Methods

- (void)keyboardWillShow:(NSNotification*)notification
{
    
    isKeyboardShowing = YES;
    
    if (isKeyboardShowing)
    {
        return;
    }
    
    //donot adjust ui
    return;
	
	if (UIInterfaceOrientationIsLandscape([self currentOrientation]))
    {
	}
	else
    {
	}
}

- (void)keyboardWillHide:(NSNotification*)notification
{
	isKeyboardShowing = NO;
	
    //donot adjust ui
    return;
    
	if (UIInterfaceOrientationIsLandscape([self currentOrientation]))
    {
	}
	else {
	}
}

#pragma mark Orientations
- (UIInterfaceOrientation)currentOrientation
{
    return [UIApplication sharedApplication].statusBarOrientation;
}

- (void)sizeToFitOrientation:(UIInterfaceOrientation)orientation
{
    if (UIInterfaceOrientationIsLandscape(orientation))
    {
        
    }
    else
    {
        
    }
    previousOrientation = orientation;
}

- (void)deviceOrientationDidChange:(id)object
{
	UIInterfaceOrientation orientation = [self currentOrientation];
	if ([self shouldRotateToOrientation:orientation])
    {
        NSTimeInterval duration = [UIApplication sharedApplication].statusBarOrientationAnimationDuration;
		
		[UIView beginAnimations:nil context:nil];
		[UIView setAnimationDuration:duration];
        [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
		[self sizeToFitOrientation:orientation];
		[UIView commitAnimations];
	}
}

- (CGAffineTransform)transformForOrientation:(UIInterfaceOrientation)orientation
{
	if (orientation == UIInterfaceOrientationLandscapeLeft)
    {
		return CGAffineTransformMakeRotation(-M_PI / 2);
	}
    else if (orientation == UIInterfaceOrientationLandscapeRight)
    {
		return CGAffineTransformMakeRotation(M_PI / 2);
	}
    else if (orientation == UIInterfaceOrientationPortraitUpsideDown)
    {
		return CGAffineTransformMakeRotation(-M_PI);
	}
    else
    {
		return CGAffineTransformIdentity;
	}
}

- (BOOL)shouldRotateToOrientation:(UIInterfaceOrientation)orientation
{
	if (orientation == previousOrientation)
    {
		return NO;
	}
    else
    {
		return orientation == UIInterfaceOrientationLandscapeLeft
		|| orientation == UIInterfaceOrientationLandscapeRight
		|| orientation == UIInterfaceOrientationPortrait
		|| orientation == UIInterfaceOrientationPortraitUpsideDown;
	}
    return YES;
}

#pragma mark Obeservers

- (void)addObservers
{
	[[NSNotificationCenter defaultCenter] addObserver:self
											 selector:@selector(deviceOrientationDidChange:)
												 name:@"UIDeviceOrientationDidChangeNotification" object:nil];
	[[NSNotificationCenter defaultCenter] addObserver:self
											 selector:@selector(keyboardWillShow:) name:@"UIKeyboardWillShowNotification" object:nil];
	[[NSNotificationCenter defaultCenter] addObserver:self
											 selector:@selector(keyboardWillHide:) name:@"UIKeyboardWillHideNotification" object:nil];
}

- (void)removeObservers
{
	[[NSNotificationCenter defaultCenter] removeObserver:self
													name:@"UIDeviceOrientationDidChangeNotification" object:nil];
	[[NSNotificationCenter defaultCenter] removeObserver:self
													name:@"UIKeyboardWillShowNotification" object:nil];
	[[NSNotificationCenter defaultCenter] removeObserver:self
													name:@"UIKeyboardWillHideNotification" object:nil];
    
    [contentTextView setDelegate:nil];
}


#pragma mark Animations

- (void)bounceOutAnimationStopped
{
    [UIView beginAnimations:nil context:nil];
	[UIView setAnimationDuration:0.13];
	[UIView setAnimationDelegate:self];
	[UIView setAnimationDidStopSelector:@selector(bounceInAnimationStopped)];
    [panelView setAlpha:0.8];
	[panelView setTransform:CGAffineTransformScale(CGAffineTransformIdentity, 0.9, 0.9)];
	[UIView commitAnimations];
}

- (void)bounceInAnimationStopped
{
    [UIView beginAnimations:nil context:nil];
	[UIView setAnimationDuration:0.13];
    [UIView setAnimationDelegate:self];
	[UIView setAnimationDidStopSelector:@selector(bounceNormalAnimationStopped)];
    [panelView setAlpha:1.0];
	[panelView setTransform:CGAffineTransformScale(CGAffineTransformIdentity, 1.0, 1.0)];
	[UIView commitAnimations];
}

- (void)bounceNormalAnimationStopped
{
    [self allAnimationsStopped];
}

- (void)allAnimationsStopped
{
//    if ([delegate respondsToSelector:@selector(snsDialogDidAppear:)])
//    {
//        [delegate snsDialogDidAppear:self];
//    }
}

#pragma mark Dismiss

- (void)hideAndCleanUp
{
    [self removeObservers];
	[self removeFromSuperview];
    
//    if ([delegate respondsToSelector:@selector(snsDialogDidDisappear:)])
//    {
//        [delegate snsDialogDidDisappear:self];
//    }
}

#pragma mark Public Methods
-(void)setTextPlaceHolder:(NSString*)tPlaceHolder
{
    m_textPlaceHolder = NSLocalizedString(tPlaceHolder, nil);
    [self SetTextPlaceHolder:contentTextView HolderText:m_textPlaceHolder];
}

- (void)show:(BOOL)animated
{
    [self sizeToFitOrientation:[self currentOrientation]];
    
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
	if (!window)
    {
		window = [[UIApplication sharedApplication].windows objectAtIndex:0];
	}
  	[window addSubview:self];
    //[window.rootViewController.view addSubview:self];
    
//    if ([delegate respondsToSelector:@selector(snsDialogWillAppear:)])
//    {
//        [delegate snsDialogWillAppear:self];
//    }
    
    if (animated)
    {
        [panelView setAlpha:0];
        CGAffineTransform transform = CGAffineTransformIdentity;
        [panelView setTransform:CGAffineTransformScale(transform, 0.3, 0.3)];
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.2];
        [UIView setAnimationDelegate:self];
        [UIView setAnimationDidStopSelector:@selector(bounceOutAnimationStopped)];
        //[self setBackgroundColor:[UIColor colorWithRed:0 green:0 blue:0 alpha:0.6f]];
        [panelView setAlpha:0.5];
        [panelView setTransform:CGAffineTransformScale(transform, 1.1, 1.1)];
        [UIView commitAnimations];
    }
    else
    {
        [self allAnimationsStopped];
    }
	
	[self addObservers];
    
}

- (void)hide:(BOOL)animated
{
//    if ([delegate respondsToSelector:@selector(snsDialogWillDisappear:)])
//    {
//        [delegate snsDialogWillDisappear:self];
//    }
    
	if (animated)
    {
		[UIView beginAnimations:nil context:nil];
		[UIView setAnimationDuration:0.3];
		[UIView setAnimationDelegate:self];
		[UIView setAnimationDidStopSelector:@selector(hideAndCleanUp)];
		self.alpha = 0;
		[UIView commitAnimations];
	} else {
		
		[self hideAndCleanUp];
	}
    
}

- (NSString*)getDialogResult;
{
    if( textHolderSeted )
    {
        return @"";
    }
    
    return contentTextView.text;
}

- (int)getDialogBtnResult
{
    return btnResult;
}
@end
